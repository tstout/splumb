package splumb.net.framing;

import java.nio.ByteBuffer;

import static splumb.net.framing.NativeFrameState.FrameStateStatus.*;
import static splumb.net.framing.NativeFramer.*;

/**
 * This provides the native splumb message framing.
 * |DEADBEEF (magic header)|Payload Length - 16 bits|Payload (64K max)|
 */
enum NativeFrameState {
    HEADER_RX {
        @Override
        FrameStateStatus process(RxContext context) {

            context.frameBuff.flip();

            if (context.frameBuff.remaining() >= 6) {
                context.setState(HEADER_AVAILABLE);
                return CONTINUE;
            }

            return WAIT;
        }

        @Override
        void entry(RxContext context) {
            //
            // Header is 6 bytes...
            //
            context.frameBuff.clear();
            context.frameBuff.limit(6);
        }
    },
    HEADER_AVAILABLE {
        @Override
        FrameStateStatus process(RxContext context) {

            if (context.frameBuff.getInt() == MAGIC) {
                context.payloadLength = context.frameBuff.getShort();
                context.setState(PAYLOAD_RX);
                return WAIT;
            }

            context.setState(HEADER_RX);
            return WAIT;
        }

        @Override
        void entry(RxContext context) {
        }
    },
    PAYLOAD_RX {
        @Override
        void entry(RxContext context) {
            context.frameBuff.clear();
            context.frameBuff.limit(context.payloadLength);
        }

        @Override
        FrameStateStatus process(RxContext context) {

            context.frameBuff.flip();

            if (context.payloadLength == context.frameBuff.remaining()) {
                context.setState(FRAME_COMPLETE);
                return CONTINUE;
            }

            return WAIT;
        }
    },
    FRAME_COMPLETE {
        @Override
        void entry(RxContext context) {
        }

        @Override
        FrameStateStatus process(RxContext context) {
            //
            // Copy payload to a new buff to pass to the frame listener
            //

            context.payload = ByteBuffer.allocate(context.payloadLength);
            context.copy(context.frameBuff, context.payload, context.payloadLength);

            context.payload.flip();
            context.frameBuff.clear();
            return WAIT;
        }
    };

    enum FrameStateStatus {
        CONTINUE,
        WAIT
    }

    abstract FrameStateStatus process(RxContext context);

    abstract void entry(RxContext context);

    public static void parse(RxContext context) {
        while (context.currentState.process(context) == CONTINUE) ;
    }

}

