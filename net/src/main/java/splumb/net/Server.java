package splumb.net;

public interface Server extends NetEndpoint {
    void listen(int port);
}
