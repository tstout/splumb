package splumb.net.framing;

import java.nio.ByteBuffer;

public interface Framer {
    ByteBuffer buffer();
    boolean isFrameComplete(int bytesJustRead);
    ByteBuffer payload();
}
