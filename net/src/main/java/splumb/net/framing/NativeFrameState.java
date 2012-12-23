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
            //context.copyFromNet();
            context.copy(6); // TODO - def const for magic 6 here...
            context.frameBuff.flip();
            if (context.frameBuff.remaining() >= 6) {
                context.setState(HEADER_AVAILABLE);
                //context.frameBuff.flip();
                return CONTINUE;
            }

            context.frameBuff.compact();
            return WAIT;
        }
    },
    HEADER_AVAILABLE {
        @Override
        FrameStateStatus process(RxContext context) {
            //context.copyFromNet();

            if (context.frameBuff.getInt() == MAGIC) {
                context.payloadLength = context.frameBuff.getShort();
                //context.frameBuff.flip();
                context.frameBuff.compact();
                context.setState(PAYLOAD_RX);
                return CONTINUE;
            }

            //context.frameBuff.clear();
            context.setState(HEADER_RX);
            return WAIT;
        }
    },
//    MAGIC_RX {
//        @Override
//        FrameStateStatus process(RxContext context) {
//            if (context.buffFromNet.remaining() < 4) {
//                context.copyFromNet();
//                context.frameBuff.put(context.buffFromNet);
//                context.setState(PARTIAL_MAGIC);
//
//                return WAIT;
//            }
//
//            if (context.buffFromNet.getInt() == MAGIC) {
//                context.setState(SIZE_RX);
//                return FrameStateStatus.CONTINUE;
//            }
//
//            return WAIT;
//        }
//    },
//    PARTIAL_MAGIC {
//        @Override
//        FrameStateStatus process(RxContext context) {
//            int bytesNeeded = 4 - context.frameBuff.position();
//            context.frameBuff.put(context.buffFromNet);
//
//            if (context.frameBuff.remaining() >= bytesNeeded) {
//                if (context.frameBuff.getInt() == MAGIC) {
//                    context.setState(SIZE_RX);
//                    return FrameStateStatus.CONTINUE;
//                }
//            }
//
//            return CONTINUE;
//        }
//    },
//    SIZE_RX {
//        @Override
//        FrameStateStatus process(RxContext context) {
//            if (context.buffFromNet.remaining() < 2) {
//
//                context.frameBuff.put(context.buffFromNet);
//                context.setState(PARTIAL_SIZE_RX);
//                return FrameStateStatus.WAIT;
//            }
//
//            context.payloadLength = context.buffFromNet.getShort();
//            context.frameBuff.clear();
//            context.setState(PAYLOAD_RX);
//            return CONTINUE;
//        }
//    },
//    PARTIAL_SIZE_RX {
//        @Override
//        FrameStateStatus process(RxContext context) {
//            context.frameBuff.put(context.buffFromNet);
//            context.payloadLength = context.buffFromNet.getShort();
//            context.frameBuff.clear();
//            context.setState(PAYLOAD_RX);
//            return WAIT;
//        }
//    },
    PAYLOAD_RX {
        @Override
        FrameStateStatus process(RxContext context) {

            // TODO - does not handle partial frame after end of current frame. Do we need another state for this
            // case?



            int bytesToRead = Math.min(context.payloadLength, context.buffFromNet.remaining());
            //int bytesToRead = Math.max(context.buffFromNet.remaining(), context.frameBuff.remaining());

            //context.frameBuff.put(context.buffFromNet.array(), context.frameBuff.position(), bytesToRead);
//            ByteBuffer tmpBuff = context.buffFromNet.duplicate();
//            tmpBuff.limit (tmpBuff.position() + bytesToRead);
//            context.frameBuff.put(tmpBuff);
//            context.frameBuff.position(context.frameBuff.position() + bytesToRead);
            //context.currentLength += bytesToRead;
            context.copy(bytesToRead);

            if (bytesToRead >= context.payloadLength) {
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
            //context.frameBuff.flip();

            ByteBuffer payload = ByteBuffer.allocate(context.payloadLength);
            context.frameBuff.flip();
            context.copy(context.frameBuff, payload, context.payloadLength);

//            payload.put(context.frameBuff.array(), 6, context.payloadLength);
//
//            context.frameBuff.position(context.frameBuff.position() + context.payloadLength);
            payload.flip();

            context.frameListener.frameAvailable(context.client, payload);

//            context.buffFromNet.position(context.buffFromNet.position() + context.payloadLength);
//            context.resetFrameBuff();



            FrameStateStatus rVal = context.frameBuff.remaining() == 0 ? WAIT : CONTINUE;

            context.frameBuff.compact();
            return rVal;
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

