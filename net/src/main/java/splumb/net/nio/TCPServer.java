package splumb.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

import static com.google.common.base.Preconditions.*;

class TCPServer implements Server {
    private ServerSocketChannel channel;
    private NIOSelect selector;
    private Boolean listening = false;
    private MsgHandler handler;

    public TCPServer(NIOSelect selector, MsgHandler handler) {
        this.selector = selector;
        this.handler = handler;

        try {
            channel = ServerSocketChannel.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listen(int port) {
        synchronized (listening) {
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
                    SelectionKey.OP_ACCEPT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
