package splumb.net.framing;

import java.nio.ByteBuffer;

/**
 * This provides the native splumb message framing.
 */
public class NativeFramer implements Framer {
    public NativeFramer()

    @Override
    public boolean isFrameComplete(ByteBuffer buff) {
        return true;
    }

    @Override
    public void postFrame() {

    }
}
