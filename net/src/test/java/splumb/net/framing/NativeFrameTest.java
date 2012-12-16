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
        NativeFrameBuilder builder = new NativeFrameBuilder();

        RxContext ctxt = new RxContext();

        final ByteBuffer payloadBuff = ByteBuffer.allocate(TEST_PAYLOAD_AS_ARRAY.length);

        ctxt.frameListener = new FrameListener() {
            @Override
            public void frameAvailable(Client client, ByteBuffer payload) {
                payloadBuff.put(payload);
                payloadBuff.flip();
            }
        };

        ctxt.buffFromNio = TEST_FRAME;

        NativeFrameState.parse(ctxt);

        assertThat(Arrays.equals(payloadBuff.array(), TEST_PAYLOAD_AS_ARRAY), is(true));
    }




}
