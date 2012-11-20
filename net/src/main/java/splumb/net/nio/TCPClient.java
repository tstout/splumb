package splumb.net.nio;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

class TCPClient implements Client {
    private Supplier<SocketChannel> sockSupplier = new Supplier<SocketChannel>() {
        public SocketChannel get() {
            return connect();
        }
    };

    private Supplier<SocketChannel> memoizedSock = Suppliers.memoize(sockSupplier);
    private InetAddress hostAddress;
    private int port;
    private MsgHandler handler;
    private NIOSelect select;

    TCPClient(
            InetAddress hostAddress,
            int port,
            MsgHandler handler,
            NIOSelect select) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.handler = handler;
        this.select = select;

        //
        // initiate connect...
        //
        sock();
    }

    @Override
    public void send(byte[] msg) {
        select.applyChange(
                new SelectorCmd(
                        sock(),
                        this,
                        ByteBuffer.wrap(msg)));
    }

    private SocketChannel connect() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            socketChannel.connect(
                    new InetSocketAddress(this.hostAddress,
                            port));

            select.applyChange(
                    new SelectorCmd(
                            socketChannel,
                            this,
                            handler,
                            SelectorOps.REGISTER,
                            SelectionKey.OP_CONNECT));

            return socketChannel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SocketChannel sock() {
        return memoizedSock.get();
    }
}
