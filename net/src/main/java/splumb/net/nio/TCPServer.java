package splumb.net.nio;

import splumb.net.framing.Framer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Throwables.*;

class TCPServer implements Server {
    private ServerSocketChannel channel;
    private NIOSelect selector;
    private Boolean listening = false;
    private final Object listenLock = new Object();
    private MsgHandler handler;
    private Framer framer;

    TCPServer(NIOSelect selector, MsgHandler handler, Framer framer) {
        this.selector = selector;
        this.handler = handler;
        this.framer = framer;

        try {
            channel = ServerSocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listen(int port) {
        synchronized (listenLock) {
            checkState(!listening, "Server already listening");
            listenOnPort(port, handler);
            listening = true;
        }
    }

    private void listenOnPort(int port, MsgHandler handler) {
        try {
            channel.configureBlocking(false);
            channel.socket().bind(new InetSocketAddress(port));

            selector.applyChange(new SelectorCmd(
                    channel,
                    this,
                    handler,
                    SelectorOps.REGISTER,
                    SelectionKey.OP_ACCEPT,
                    framer));
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    @Override
    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            throw propagate(e);
        }
    }
}
