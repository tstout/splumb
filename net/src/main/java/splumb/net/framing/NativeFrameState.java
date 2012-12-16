package splumb.net.framing;

import java.nio.ByteBuffer;

import static splumb.net.framing.NativeFrameState.FrameStateStatus.*;
import static splumb.net.framing.NativeFramer.*;
/**
 * This provides the native splumb message framing.
 * |DEADBEEF (magic header)|Payload Length - 16 bits|Payload (64K max)|
 */
enum NativeFrameState {
    MAGIC_RX {
        @Override
        FrameStateStatus process(RxContext context) {
            if (context.buffFromNet.limit() < 4) {
                context.frameBuff.put(context.buffFromNet);
                context.setState(PARTIAL_MAGIC);

                return WAIT;
            }

            if (context.buffFromNet.getInt() == MAGIC) {
                context.setState(SIZE_RX);
                return FrameStateStatus.CONTINUE;
            }

            return WAIT;
        }
    },
    PARTIAL_MAGIC {
        @Override
        FrameStateStatus process(RxContext context) {
            context.frameBuff.put(context.buffFromNet);

            int bytesNeeded = 4 - context.frameBuff.limit();

            if (context.buffFromNet.limit() >= bytesNeeded) {
                if (context.buffFromNet.getInt() == MAGIC) {
                    context.setState(SIZE_RX);
                    return FrameStateStatus.CONTINUE;
                }
            }

            return CONTINUE;
        }
    },
    SIZE_RX {
        @Override
        FrameStateStatus process(RxContext context) {
            if (context.buffFromNet.limit() < 2) {

                context.frameBuff.put(context.buffFromNet);
                context.setState(PARTIAL_SIZE_RX);
                return FrameStateStatus.WAIT;
            }

            context.payloadLength = context.buffFromNet.getShort();
            context.frameBuff.clear();
            context.setState(PAYLOAD_RX);
            return CONTINUE;
        }
    },
    PARTIAL_SIZE_RX {
        @Override
        FrameStateStatus process(RxContext context) {
            context.frameBuff.put(context.buffFromNet);
            context.payloadLength = context.buffFromNet.getShort();
            context.frameBuff.clear();
            context.setState(PAYLOAD_RX);
            return WAIT;
        }
    },
    PAYLOAD_RX {
        @Override
        FrameStateStatus process(RxContext context) {

            // TODO - does not handle partial frame after end of current frame. Do we need another state for this
            // case?
            int bytesToRead = Math.min(context.payloadLength - context.currentLength, context.buffFromNet.remaining());
            context.frameBuff.put(context.buffFromNet.array(), context.buffFromNet.position(), bytesToRead);
            context.currentLength += bytesToRead;

            if (context.currentLength == context.payloadLength) {
                context.setState(FRAME_COMPLETE);
                return CONTINUE;
            }

            return WAIT;
        }
    },
    FRAME_COMPLETE {
        @Override
        FrameStateStatus process(RxContext context) {
            //
            // Copy payload to a new buff to pass to the frame listener
            //
            context.frameBuff.flip();
            ByteBuffer payload = ByteBuffer.allocate(context.payloadLength);
            payload.put(context.frameBuff);
            payload.flip();

            context.frameListener.frameAvailable(context.client, payload);

            context.buffFromNet.position(context.buffFromNet.position() + context.payloadLength);
            context.resetFrameBuff();

            return context.buffFromNet.remaining() == 0 ? WAIT : CONTINUE;
        }
    };

    enum FrameStateStatus {
        CONTINUE,
        WAIT
    }

    abstract FrameStateStatus process(RxContext context);

    public static void parse(RxContext context) {
        while(context.currentState.process(context) == CONTINUE);
    }

}

