package splumb.net.framing;

import splumb.net.nio.Client;

import java.nio.ByteBuffer;

public interface FrameListener {
    void frameAvailable(Client client, ByteBuffer payload);
}
