package splumb.net.framing;

import java.nio.ByteBuffer;

/**
 * This provides the native splumb message framing.
 * |DEADBEEF (magic header)|Payload Length - 16 bits|Payload (64K max)|
 */
enum NativeFrameState {
    MAGIC_RX {
        @Override
        FrameStateStatus process(RxContext context) {
            if (context.buffFromNio.remaining() < 4) {
                return FrameStateStatus.INCOMPLETE;
            }

            if (context.buffFromNio.getInt() == NativeFramer.MAGIC) {
                context.setState(SIZE_RX);
                return FrameStateStatus.COMPLETE;
            }

            return FrameStateStatus.INCOMPLETE;
        }

        @Override
        void init(RxContext context) {
        }
    },
    SIZE_RX {
        @Override
        FrameStateStatus process(RxContext context) {
            if (context.buffFromNio.remaining() < 2) {
                return FrameStateStatus.INCOMPLETE;
            }

            context.payloadLength = context.buffFromNio.getShort();
            return FrameStateStatus.COMPLETE;
        }

        @Override
        void init(RxContext context) {

        }
    },
    PAYLOAD_RX {
        @Override
        void init(RxContext context) {

        }

        @Override
        FrameStateStatus process(RxContext context) {

            // TODO - does not handle partial frame after end of current frame. Do we need another state for this
            // case?
            int bytesToRead = Math.max(context.payloadLength - context.currentLength, context.buffFromNio.remaining());
            context.frameBuff.put(context.buffFromNio.array(), 0, bytesToRead);
            context.currentLength += bytesToRead;

            if (context.currentLength == context.payloadLength) {
                context.setState(FRAME_COMPLETE);
                return FrameStateStatus.COMPLETE;
            }

            return FrameStateStatus.INCOMPLETE;
        }
    },
    FRAME_COMPLETE {
        @Override
        FrameStateStatus process(RxContext context) {
            //
            // Copy payload to a new buff to pass to the frame listener
            //
            ByteBuffer payload = ByteBuffer.allocate(context.payloadLength);
            context.frameBuff.get(payload.array());
            context.frameListener.frameAvailable(context.client, payload);

            // TODO - need to safely copy any possible sequential frame from
            // the buffer here.  Maybe put his copy logic into resetFrameBuff();
            context.resetFrameBuff();
            return FrameStateStatus.INCOMPLETE;
        }

        @Override
        void init(RxContext context) {

        }
    };

    enum FrameStateStatus {
        COMPLETE,
        INCOMPLETE
    }

    enum ParseStatus {
        FINISHED,
        NOT_FINISHED
    }

    abstract FrameStateStatus process(RxContext context);
    abstract void init(RxContext context);

    void parse(RxContext context) {
        while(context.currentState.process(context) == FrameStateStatus.COMPLETE);
        //return context.currentState == FRAME_COMPLETE ? ParseStatus.FINISHED : ParseStatus.NOT_FINISHED;
    }

}

