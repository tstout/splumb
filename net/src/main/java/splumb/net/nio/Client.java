package splumb.net.nio;

import java.nio.ByteBuffer;

public interface Client extends NetEndpoint {
    // TODO - how about a ByteBuffer here instead of byte[]?
    void send(byte[] data);
    void send(ByteBuffer data);
}
