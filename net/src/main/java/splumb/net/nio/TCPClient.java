package splumb.net.nio;

import com.google.common.eventbus.Subscribe;
import splumb.net.framing.Framer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Lists.*;

// TODO - cleanup synchronized warnings in this code...

class TCPClient implements Client {

    private InetAddress hostAddress;
    private int port;
    private MsgHandler handler;
    private NIOSelect select;
    private ScheduledExecutorService scheduler;
    private Reconnect pendingConnect;
    private Boolean connected = false;
    private SelectableChannel selectableChannel;
    private Transmitter transmitter = new QueueingTransmitter();
    private Framer framer;

    TCPClient(InetAddress hostAddress,
              int port,
              MsgHandler handler,
              NIOSelect select,
              ScheduledExecutorService scheduler,
              Framer framer) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.handler = handler;
        this.select = select;
        this.scheduler = scheduler;
        this.framer = framer;
        connect();
    }

    @Override
    public void send(byte[] msg) {
        synchronized (transmitter) {
            transmitter.send(msg);
        }
    }

    @Override
    public void send(ByteBuffer data) {
        send(data.array());
    }

    private void connect() {
        try {

            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selectableChannel = socketChannel;

            boolean rc = socketChannel.connect(
                    new InetSocketAddress(this.hostAddress,
                            port));

            select.applyChange(
                    new SelectorCmd(
                            socketChannel,
                            this,
                            handler,
                            SelectorOps.REGISTER,
                            SelectionKey.OP_CONNECT,
                            framer));

            pendingConnect = new Reconnect();

            // TODO - retry interval should be static const or configurable...
            scheduler.schedule(pendingConnect, 500, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    // TODO - replace connected with an AtomicBoolean...

    private void setConnected(boolean value) {
        synchronized (connected) {
            connected = value;
        }
    }

    private boolean isConnected() {
        synchronized (connected) {
            return connected;
        }
    }

    @Subscribe
    public void connected(SocketConnectedEvent socketConnectedEvent) {
        if (selectableChannel == socketConnectedEvent.getSelectableChannel()) {

            synchronized (transmitter) {
                selectableChannel = socketConnectedEvent.getSelectableChannel();
                ConnectedTransmitter connectedTransmitter = new ConnectedTransmitter();
                transmitter.sendAll(connectedTransmitter);
                transmitter = connectedTransmitter;

                setConnected(true);
            }
        }
    }

    @Override
    public void close() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    class Reconnect implements Runnable {

        @Override
        public void run() {
            if (!isConnected()) {
                connect();
            }
        }
    }

    interface Transmitter {
        void send(byte[] msg);

        void sendAll(Transmitter transmitter);

    }

    class QueueingTransmitter implements Transmitter {
        private List<byte[]> queue = newArrayList();

        @Override
        public void send(byte[] msg) {
            queue.add(msg);
        }

        @Override
        public void sendAll(Transmitter transmitter) {
            for (byte[] data : queue) {
                transmitter.send(data);
            }
        }
    }

    class ConnectedTransmitter implements Transmitter {

        @Override
        public void send(byte[] msg) {
            select.applyChange(
                    new SelectorCmd(
                            selectableChannel,
                            TCPClient.this,
                            ByteBuffer.wrap(msg), framer));
        }

        @Override
        public void sendAll(Transmitter transmitter) {
        }
    }

}
