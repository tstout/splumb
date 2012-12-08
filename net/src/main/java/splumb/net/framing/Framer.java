package splumb.net.framing;

import java.nio.ByteBuffer;

public interface Framer {
    boolean isFrameComplete(ByteBuffer buff);
    void postFrame();
}
