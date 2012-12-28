package splumb.net.framing;

import com.google.common.primitives.Ints;

import java.nio.ByteBuffer;

class FramingConstants {
    static final int LISTEN_PORT = 8000;

    static final int PAYLOAD = 0xCAFEBABE;
    static final byte[] TEST_PAYLOAD = Ints.toByteArray(PAYLOAD);

    private static final ByteBuffer TEST_FRAME =
            new NativeFrameBuilder()
            .withPayload(TEST_PAYLOAD, (short)(TEST_PAYLOAD.length))
            .build();

    static ByteBuffer testFrame() {
        return new NativeFrameBuilder()
                .withPayload(TEST_PAYLOAD, (short)(TEST_PAYLOAD.length))
                .build();
    }
}
