package splumb.net.nio;

public interface Server extends NetEndpoint {
    void listen(int port);
}
