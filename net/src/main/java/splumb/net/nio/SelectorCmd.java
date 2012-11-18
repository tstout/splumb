package splumb.net.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;

//
// TODO - refactor this into separate command classes
//
class SelectorCmd {
    public SelectableChannel socket;
    public NetEndpoint channel;
    public SelectorOps type;
    public int ops;
    public ByteBuffer data;
    public MsgHandler handler;

    public SelectorCmd(
            SelectableChannel socket,
            NetEndpoint channel,
            MsgHandler handler,
            SelectorOps type,
            int ops) {
        this.socket = socket;
        this.type = type;
        this.ops = ops;
        this.channel = channel;
        this.handler = handler;
    }

    public SelectorCmd(SelectableChannel socket, NetEndpoint channel, ByteBuffer data) {
        this.socket = socket;
        this.channel = channel;
        this.data = data;
        type = SelectorOps.TRANSMIT;
    }
}

