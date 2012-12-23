package splumb.net.framing;

import org.junit.Test;
import splumb.net.nio.Client;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static splumb.net.framing.FramingConstants.*;

public class NativeFrameTest {

    @Test
    public void parseSimpleFrameTest() {
        RxContext ctxt = new RxContext();
        Listener listener = new Listener();

        ctxt.frameListener = listener;
        ctxt.buffFromNet = testFrame();//TEST_FRAME;

        NativeFrameState.parse(ctxt);

        assertThat(Arrays.equals(listener.payload.array(), TEST_PAYLOAD_AS_ARRAY), is(true));
    }

    @Test
    public void parseMultipleFrameTest() {
        //NativeFrameBuilder builder = new NativeFrameBuilder();

        RxContext ctxt = new RxContext();
        MultiFrameListener listener = new MultiFrameListener();

        ctxt.frameListener = listener;
        ByteBuffer twoFrames = ByteBuffer.allocate(testFrame().array().length * 2);

        twoFrames.put(testFrame());
        twoFrames.put(testFrame());
        twoFrames.flip();
        ctxt.buffFromNet = twoFrames;

        NativeFrameState.parse(ctxt);
        assertThat(listener.frameCount, is(2));
    }

    @Test
    public void partialSingleFrameTest() {
        RxContext ctxt = new RxContext();

        ByteBuffer partialHeader = ByteBuffer.allocate(2);
        partialHeader.putShort((short)0xDEAD);
        partialHeader.flip();

        MultiFrameListener listener = new MultiFrameListener();
        ctxt.frameListener = listener;
        ctxt.buffFromNet = partialHeader;


        NativeFrameState.parse(ctxt);

        ByteBuffer partialFrame = ByteBuffer.allocate(8);
        partialFrame.putShort((short)0xBEEF);
        partialFrame.putShort((short)4);
        partialFrame.put(TEST_PAYLOAD_AS_ARRAY);
        partialFrame.flip();

        ctxt.buffFromNet = partialFrame;
        NativeFrameState.parse(ctxt);
        assertThat(listener.frameCount, is(1));
    }
}

class MultiFrameListener implements FrameListener {

    int frameCount;

    @Override
    public void frameAvailable(Client client, ByteBuffer payload) {
        frameCount++;
        assertThat(Arrays.equals(payload.array(), TEST_PAYLOAD_AS_ARRAY), is(true));
    }
}

class Listener implements FrameListener {

    public ByteBuffer payload;

    @Override
    public void frameAvailable(Client client, ByteBuffer payload) {
        this.payload = payload;
    }
}