package splumb.net.nio;

import java.nio.channels.SelectableChannel;

class SocketConnectedEvent {
    private final SelectableChannel selectableChannel;

    public SocketConnectedEvent(SelectableChannel socketChannel) {
        this.selectableChannel = socketChannel;
    }

    public SelectableChannel getSelectableChannel() {
        return selectableChannel;
    }
}
