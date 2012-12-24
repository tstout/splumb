package splumb.net.nio;

import splumb.net.framing.Framer;

import java.nio.ByteBuffer;

public class TestFramer implements Framer {
    private ByteBuffer buffer = ByteBuffer.allocate(1024 * 64);

    @Override
    public ByteBuffer buffer() {
        return buffer;
    }

    @Override
    public boolean isFrameComplete(int bytesJustRead) {
        buffer.flip();
        return true;
    }

    @Override
    public ByteBuffer payload() {
        return buffer;
    }

}
