package splumb.net;

public interface Client extends NetEndpoint {
    void send(byte[] data);
}
