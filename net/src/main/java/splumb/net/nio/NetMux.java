package splumb.net.nio;

public interface NetMux {
    NetEndpoint newClient(NetAddr addr, MsgHandler handler);

    void newServer(NetAddr addr, MsgHandler handler);
}
