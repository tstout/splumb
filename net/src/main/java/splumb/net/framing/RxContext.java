package splumb.net.framing;

import splumb.net.nio.Client;

import java.nio.ByteBuffer;

class RxContext {
    ByteBuffer payload;
    ByteBuffer frameBuff;
    NativeFrameState currentState;
    Client client;
    short payloadLength;
    //short currentLength;
    FrameListener frameListener;

    RxContext() {
        frameBuff = ByteBuffer.allocate(NativeFramer.MAX_FRAME_LENGTH);
        //resetFrameBuff();

    }
//
//    RxContext copy(int max) {
//        copy(buffFromNet, frameBuff, max);
////        int maxTransfer = Math.min(max, buffFromNet.remaining());
////        ByteBuffer tmp = buffFromNet.duplicate();
////        tmp.limit (tmp.position() + maxTransfer);
////        frameBuff.put (tmp);
////
////        buffFromNet.position(buffFromNet.position() + maxTransfer);
//        return this;
//    }

    RxContext copy(ByteBuffer src, ByteBuffer dest, int max) {
        int maxTransfer = Math.min(max, src.remaining());
        ByteBuffer tmp = src.duplicate();
        tmp.limit (tmp.position() + maxTransfer);
        dest.put (tmp);

        src.position(src.position() + maxTransfer);
        return this;
    }


//    RxContext copyFromNet() {
////        int sizeToCopy = buffFromNet.remaining() + frameBuff.position();
////        int pos = frameBuff.position();
//
//        frameBuff.put(buffFromNet);
//        //frameBuff.compact();
//        frameBuff.flip();
//
//        return this;
//    }

//    RxContext resetFrameBuff() {
//        frameBuff.clear();
//        //currentLength = 0;
//        currentState = NativeFrameState.HEADER_RX;
//        return this;
//    }

    RxContext setState(NativeFrameState currentState) {
        this.currentState = currentState;
        this.currentState.entry(this);
        return this;
    }
}

