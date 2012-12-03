package splumb.net.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;


//
// MsgChannel created after accept...
//
class InternalChannel implements Client {
    NIOSelect select;
    SelectableChannel sock;

    InternalChannel(NIOSelect select, SelectableChannel sock) {
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

    @Override
    public void close() {
    }
}
