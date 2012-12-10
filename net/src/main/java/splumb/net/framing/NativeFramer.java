package splumb.net.framing;

import java.nio.ByteBuffer;

/**
 * This provides the native splumb message framing.
 * |DEADBEEF (magic header)|Payload Length - 16 bits|Payload (64K max)|
 */
public class NativeFramer implements Framer {

    public static final int MAX_FRAME_LENGTH = 64 * 1024;

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
        buff = ByteBuffer.allocate(NativeFramer.MAX_FRAME_LENGTH);
    }

    ByteBuffer buff;
    ByteBuffer currentBuff;
    FrameState currentState;
    int payloadLength;
    int currentLength;
}

enum FrameState {
    MAGIC_RX {
        @Override
        void process(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        void init(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    },
    SIZE_RX {
        @Override
        void process(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        void init(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    },
    PAYLOAD_RX {
        @Override
        void init(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        void process(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    },
    FRAME_COMPLETE {
        @Override
        void process(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        void init(RxContext context) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    abstract void process(RxContext context);
    abstract void init(RxContext context);
}