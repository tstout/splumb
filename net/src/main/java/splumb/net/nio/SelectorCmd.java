package splumb.net.nio;

import splumb.net.framing.Framer;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;

//
// TODO - refactor this into separate command classes
//
class SelectorCmd {
    SelectableChannel socket;
    NetEndpoint channel;
    SelectorOps type;
    int ops;
    ByteBuffer data;
    MsgHandler handler;
    Framer framer;

    SelectorCmd(
            SelectableChannel socket,
            NetEndpoint channel,
            MsgHandler handler,
            SelectorOps type,
            int ops,
            Framer framer) {
        this.socket = socket;
        this.type = type;
        this.ops = ops;
        this.channel = channel;
        this.handler = handler;
        this.framer = framer;
    }

    SelectorCmd(SelectableChannel socket, NetEndpoint channel, ByteBuffer data, Framer framer) {
        this.socket = socket;
        this.channel = channel;
        this.data = data;
        this.framer = framer;
        type = SelectorOps.TRANSMIT;

    }

//    class Builder {
//        private SelectableChannel socket;
//        private NetEndpoint channel;
//        private SelectorOps type;
//        private int ops;
//        private ByteBuffer data;
//        private MsgHandler handler;
//        private Framer framer;
//
//        Builder withChannel(SelectableChannel channel) {
//            this.socket = channel;
//            return this;
//        }
//
//        Builder withNetEndpoint(NetEndpoint netEndpoint) {
//            this.channel = netEndpoint;
//            return this;
//        }
//
//        Builder withSelectorOps(SelectorOps type) {
//            this.type = type;
//            return this;
//        }
//
//        Builder withOps(int ops) {
//            this.ops = ops;
//            return this;
//        }
//
//        Builder withHandler(MsgHandler handler)
//    }


}

