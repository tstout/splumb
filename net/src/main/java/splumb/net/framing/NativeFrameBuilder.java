package splumb.net.framing;

import com.google.common.primitives.Ints;

import java.nio.ByteBuffer;

public class NativeFrameBuilder {
    public static final int MAGIC = 0xDEADBEEF;
    public static final byte[] MAGIC_AS_ARRAY = Ints.toByteArray(MAGIC);

    private short length;
    private byte[] payload;

    public ByteBuffer build() {
        ByteBuffer buff = ByteBuffer.allocate(MAGIC_AS_ARRAY.length + length + 2);
        buff.put(MAGIC_AS_ARRAY);
        buff.putShort((short)length);
        buff.put(payload);
        buff.flip();
        return buff;
    }

    public NativeFrameBuilder withPayload(byte[] payload, short length) {
        this.payload = payload;
        this.length = length;
        return this;
    }
}
