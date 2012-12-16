package splumb.net.framing;

import splumb.net.nio.Client;

import java.nio.ByteBuffer;


public class NativeFramer implements Framer {

    public static final int MAX_FRAME_LENGTH = 6 + (64 * 1024);
    public static final int MAGIC = 0xDEADBEEF;
    public static final int MAGIC_LENGTH = 4;

    public NativeFramer() {
    }

    @Override
    public boolean isFrameComplete(ByteBuffer buff) {
        return true;
    }

    @Override
    public void postFrame() {
    }
}

class RxContext {

    RxContext() {
        resetFrameBuff();
        currentState = NativeFrameState.MAGIC_RX;
    }

    RxContext resetFrameBuff() {
        frameBuff = ByteBuffer.allocate(NativeFramer.MAX_FRAME_LENGTH);
        return this;
    }

    RxContext setState(NativeFrameState currentState) {
        this.currentState.init(this);
        this.currentState = currentState;
        return this;
    }

    NativeFrameState currentState() {
        return currentState;
    }

    ByteBuffer buffFromNio;
    ByteBuffer frameBuff;
    NativeFrameState currentState;
    Client client;
    short payloadLength;
    short currentLength;
    FrameListener frameListener;
}




