package splumb.net.nio;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import splumb.common.logging.LogPublisher;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class NIOSelect implements Runnable {

    private ConcurrentLinkedQueue<SelectorCmd> pendingChanges =
            new ConcurrentLinkedQueue<SelectorCmd>();

    private Map<SelectableChannel, MsgHandler> rspHandlers =
            new ConcurrentHashMap<SelectableChannel, MsgHandler>();

    private Map<SelectableChannel, List<ByteBuffer>> pendingData =
            new ConcurrentHashMap<SelectableChannel, List<ByteBuffer>>();

    private Map<SelectionKey, NetEndpoint> channelMap =
            new ConcurrentHashMap<SelectionKey, NetEndpoint>();

    //
    // TODO - this buff size needs to be configurable
    //
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

    private Executor workerThr;

    private Executor selectThr;

    private NIOWorker worker;

    private Selector selector;

    private LogPublisher tracer;

    NIOSelect(LogPublisher tracer) {
        this.tracer = tracer;
        worker = new NIOWorker();

        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        selectThr = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .build());

        workerThr = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .build());

        selectThr.execute(this);
        workerThr.execute(worker);
    }

    public void applyChange(SelectorCmd cmd) {
        tracer.info("applyChange");

        if (cmd.handler != null) {
            rspHandlers.put(cmd.socket, cmd.handler);
        }

        synchronized (pendingChanges) {
            pendingChanges.add(cmd);
        }

        selector.wakeup();
    }

    @Override
    public void run() {
        SelectorEnv env = new SelectorEnv();
        env.selector = selector;
        env.channelMap = channelMap;
        env.pendingData = pendingData;

        for (; ; ) {
            try {
                synchronized (pendingChanges) {
                    for (SelectorCmd change : pendingChanges) {
                        env.channel = change.channel;
                        env.socket = change.socket;
                        env.ops = change.ops;
                        env.data = change.data;
                        env.trace = tracer;

                        change.type.process(env);
                    }

                    pendingChanges.clear();
                }

                selector.select();

                //
                // TODO - consider refactoring this if-else tree...
                //
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if (!key.isValid()) {
                        tracer.error("Invalid key");
                        continue;
                    }

                    if (key.isAcceptable()) {
                        tracer.info("Processing accept");
                        accept(key);
                    } else if (key.isConnectable()) {
                        tracer.info("Processing finishConnection");
                        finishConnection(key);
                    } else if (key.isReadable()) {
                        tracer.info("Processing read");
                        read(key);
                    } else if (key.isWritable()) {
                        tracer.info("Processing write");
                        write(key);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        readBuffer.clear();

        int numRead;
        try {
            numRead = socketChannel.read(readBuffer);
        } catch (IOException e) {
            //
            // far end closed the connection, cancel the selection key and
            // close the channel.
            //
            key.cancel();

            try {
                channelMap.remove(key);
                socketChannel.close();
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }

            return;
        }

        if (numRead == -1) {
            //
            // Remote shutdown...close the connection.
            //
            try {
                channelMap.remove(key);
                key.channel().close();
                tracer.info("Read detected remote shutdown");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            key.cancel();
            return;
        }

        worker.processData(
                channelMap.get(key),
                socketChannel,
                readBuffer.array(),
                numRead,
                rspHandlers.get(socketChannel));

        tracer.info("Posted data to worker");
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        List<ByteBuffer> queue = pendingData.get(socketChannel);

        //
        // Empty the transmit queue...
        //
        while (!queue.isEmpty()) {
            ByteBuffer buf = (ByteBuffer) queue.get(0);
            socketChannel.write(buf);

            if (buf.remaining() > 0) {
                // ... or the socket's buffer fills up
                break;
            }
            queue.remove(0);
        }

        if (queue.isEmpty()) {
            //
            // All data transmitted, switch back to read...
            //
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
                .channel();

        SocketChannel socketChannel = serverSocketChannel.accept();

        socketChannel.configureBlocking(false);

        rspHandlers.put(socketChannel, rspHandlers.get(serverSocketChannel));

        //
        // TODO - use SelectionKey.attach() instead of separate channelMap...
        //
        SelectionKey newKey = socketChannel.register(selector,
                SelectionKey.OP_READ);

        channelMap.put(newKey, new InternalChannel(this, socketChannel));

        tracer.info("Server Accepted");
    }

    //
    // MsgChannel created after accept...
    //
    class InternalChannel implements Client {
        NIOSelect select;
        SelectableChannel sock;

        public InternalChannel(NIOSelect select, SelectableChannel sock) {
            this.select = select;
            this.sock = sock;
        }

        @Override
        public void send(byte[] msg) {
            select.applyChange(
                    new SelectorCmd(
                            sock,
                            this,
                            ByteBuffer.wrap(msg)));
        }
    }

    private void finishConnection(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        try {
            socketChannel.finishConnect();
            //tracer.log("Client Connected");
        } catch (IOException e) {
            key.cancel();
            return;
        }

        key.interestOps(key.interestOps() | SelectionKey.OP_READ);
    }


}
