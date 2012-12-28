package splumb.net.framing;

import com.google.common.primitives.Ints;

import java.nio.ByteBuffer;

class FramingConstants {
    static final int LISTEN_PORT = 8000;

    static final int PAYLOAD1 = 0xCAFEBABE;
    static final int PAYLOAD2 = 0xBEEFCAFE;
    static final byte[] TEST_PAYLOAD_1 = Ints.toByteArray(PAYLOAD1);
    static final byte[] TEST_PAYLOAD_2 = Ints.toByteArray(PAYLOAD2);

    private static final ByteBuffer TEST_FRAME =
            new NativeFrameBuilder()
            .withPayload(TEST_PAYLOAD_1, (short)(TEST_PAYLOAD_1.length))
            .build();

    static ByteBuffer testFrame() {
        return new NativeFrameBuilder()
                .withPayload(TEST_PAYLOAD_1, (short)(TEST_PAYLOAD_1.length))
                .build();
    }
}
