package splumb.net.framing;

import com.google.common.primitives.Ints;

import java.nio.ByteBuffer;

class FramingConstants {

    static final int PAYLOAD = 0xCAFEBABE;
    static final byte[] TEST_PAYLOAD_AS_ARRAY = Ints.toByteArray(PAYLOAD);

    static final ByteBuffer TEST_FRAME =
            new NativeFrameBuilder()
            .withPayload(TEST_PAYLOAD_AS_ARRAY, (short)(TEST_PAYLOAD_AS_ARRAY.length))
            .build();
}
