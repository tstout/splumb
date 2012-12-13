package splumb.net.nio;

import java.nio.ByteBuffer;

public class ByteBuffers {
    public static ByteBuffer copy(ByteBuffer src) {
        ByteBuffer clone = ByteBuffer.allocate(src.capacity());
        src.rewind();
        clone.put(src);
        src.rewind();
        clone.flip();

        return clone;
    }
}
