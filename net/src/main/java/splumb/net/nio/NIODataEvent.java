package splumb.net.nio;

import java.nio.channels.SocketChannel;

class NIODataEvent
{
    Client src;
    SocketChannel socket;
    byte[] data;
    MsgHandler handler;

    NIODataEvent(
            Client src,
            SocketChannel socket,
            byte[] data,
            MsgHandler handler)
    {
        this.src = src;
        this.socket = socket;
        this.data = data;
        this.handler = handler;
    }
}
