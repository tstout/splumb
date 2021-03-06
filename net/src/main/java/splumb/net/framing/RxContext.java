package splumb.net.framing;

import java.nio.ByteBuffer;

class RxContext {
    ByteBuffer payload;
    ByteBuffer frameBuff;
    NativeFrameState currentState;
    short payloadLength;

    RxContext() {
        frameBuff = ByteBuffer.allocate(NativeFramer.MAX_FRAME_LENGTH);
    }

    RxContext copy(ByteBuffer src, ByteBuffer dest, int max) {
        int maxTransfer = Math.min(max, src.remaining());
        ByteBuffer tmp = src.duplicate();
        tmp.limit (tmp.position() + maxTransfer);
        dest.put (tmp);

        src.position(src.position() + maxTransfer);
        return this;
    }

    RxContext setState(NativeFrameState currentState) {
        this.currentState = currentState;
        this.currentState.entry(this);
        return this;
    }
}

