package splumb.net.nio;

public interface Client extends NetEndpoint {
    // TODO - how about a ByteBuffer here instead of byte[]?
    void send(byte[] data);
}
