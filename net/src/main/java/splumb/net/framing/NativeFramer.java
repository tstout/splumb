package splumb.net.framing;

import java.nio.ByteBuffer;

public class NativeFramer implements Framer {
    @Override
    public boolean isFrameComplete(ByteBuffer buff) {
        return true;
    }

    @Override
    public void postFrame() {

    }
}
