package splumb.net.nio;

import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoint;

import java.nio.channels.SocketChannel;

class NIODataEvent
{
    public NetEndpoint src;
    public SocketChannel socket;
    public byte[] data;
    public MsgHandler handler;

    public NIODataEvent(
            NetEndpoint src,
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
