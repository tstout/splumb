package splumb.net.nio;

public interface Client extends NetEndpoint {
    void send(byte[] data);
}
