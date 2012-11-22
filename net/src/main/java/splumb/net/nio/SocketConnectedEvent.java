package splumb.net.nio;

import java.nio.channels.SelectableChannel;

class SocketConnectedEvent {
    private final SelectableChannel socketChannel;

    public SocketConnectedEvent(SelectableChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public SelectableChannel getSocketChannel() {
        return socketChannel;
    }
}
