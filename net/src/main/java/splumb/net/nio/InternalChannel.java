package splumb.net.nio;

import splumb.net.framing.Framer;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;


//
// MsgChannel created after accept...
//
class InternalChannel implements Client {
    NIOSelect select;
    SelectableChannel sock;
    Framer framer;

    InternalChannel(NIOSelect select, SelectableChannel sock, Framer framer) {
        this.select = select;
        this.sock = sock;
        this.framer = framer;
    }

    @Override
    public void send(byte[] msg) {
        select.applyChange(
                new SelectorCmd(
                        sock,
                        this,
                        ByteBuffer.wrap(msg), framer));
    }

    @Override
    public void close() {
    }
}
