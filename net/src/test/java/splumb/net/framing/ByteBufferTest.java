package splumb.net.framing;

import com.google.common.base.Charsets;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
//
// Basic tests to verify my understanding of the ByteBuffer API
//
public class ByteBufferTest {
    static final int MAGIC = 0xDEADBEEF;
    static final byte[] MAGIC_AS_ARRAY = Ints.toByteArray(MAGIC);
    static final int PAYLOAD = 0xCAFEBABE;
    static final byte[] TEST_PAYLOAD_AS_ARRAY = Ints.toByteArray(PAYLOAD);

    @Test
    public void appendTest()  {

        ByteBuffer buff = ByteBuffer.allocate(100);

        String first = new String("ABC".getBytes(Charsets.UTF_8));
        String second = new String("DEF".getBytes(Charsets.UTF_8));

        buff.limit(6);
        buff.put(first.getBytes());
        buff.put(second.getBytes());

        assertThat(buff.hasRemaining(), is(false));

        ByteBuffer refBuff = ByteBuffer.wrap(new String("ABCDEF").getBytes(Charsets.UTF_8));
        buff.flip();

        assertThat(buff.compareTo(refBuff), is(0));
    }

    @Test
    public void fillFrameTest() {
        ByteBuffer buff = createFrame();

        assertThat(buff.limit(), is(10));
        assertThat(buff.remaining(), is(0));
    }

    @Test
    public void parseFrameTest() {
        ByteBuffer buff = createFrame();
        buff.flip();

        byte[] payload = new byte[TEST_PAYLOAD_AS_ARRAY.length];

        assertThat(buff.getInt(), is(MAGIC));
        assertThat(buff.getShort(), is((short)TEST_PAYLOAD_AS_ARRAY.length));

        buff.get(payload);

        assertThat(Ints.fromByteArray(payload), is(PAYLOAD));
    }

    ByteBuffer createFrame() {
        ByteBuffer buff = ByteBuffer.allocate(10);
        buff.limit(10);
        buff.put(MAGIC_AS_ARRAY);
        buff.putShort((short) (TEST_PAYLOAD_AS_ARRAY.length));
        buff.put(TEST_PAYLOAD_AS_ARRAY);

        return buff;
    }

}
