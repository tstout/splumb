package splumb.net.framing;

import java.nio.ByteBuffer;


public class NativeFramer implements Framer {

    public static final int MAX_FRAME_LENGTH = 6 + (64 * 1024);
    public static final int MAGIC = 0xDEADBEEF;
    public static final int MAGIC_LENGTH = 4;

    private RxContext context = new RxContext();

    public NativeFramer() {
        context.setState(NativeFrameState.HEADER_RX);
    }

    @Override
    public ByteBuffer buffer() {
       return context.frameBuff;
    }

    @Override
    public boolean isFrameComplete(int bytesJustRead) {
        NativeFrameState.parse(context);

        if (context.currentState == NativeFrameState.FRAME_COMPLETE) {
            context.setState(NativeFrameState.HEADER_RX);
            return true;
        }
        return false;
    }

    @Override
    public ByteBuffer payload() {
        return context.payload;
    }
}





