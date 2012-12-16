package splumb.net.framing;

import com.google.common.base.Charsets;
import com.google.common.primitives.Ints;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.net.framing.FramingConstants.*;
//
// Basic tests to verify my understanding of the ByteBuffer API
//
public class ByteBufferTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

        assertThat(buff.getInt(), is(NativeFrameBuilder.MAGIC));
        assertThat(buff.getShort(), is((short)TEST_PAYLOAD_AS_ARRAY.length));

        buff.get(payload);

        assertThat(Ints.fromByteArray(payload), is(PAYLOAD));
    }

    @Test
    public void exceedLimitTest() {
        thrown.expect(BufferOverflowException.class);

        ByteBuffer buff = createFrame();
        buff.putInt(0xFF);
    }

    @Test
    public void defaultLimitTest() {
        ByteBuffer buff = ByteBuffer.allocate(10);
        assertThat(buff.limit(), not(0));
    }

    @Test
    public void remainingTest() {
        ByteBuffer buff = ByteBuffer.allocate(10);
        buff.putInt(0xFFFFEEEE);
        //buffFromNio.mark();
        assertThat(buff.remaining(), is(10 - 4));
    }


    ByteBuffer createFrame() {
        ByteBuffer buff = ByteBuffer.allocate(10);
        buff.put(NativeFrameBuilder.MAGIC_AS_ARRAY);
        buff.putShort((short) (TEST_PAYLOAD_AS_ARRAY.length));
        buff.put(TEST_PAYLOAD_AS_ARRAY);

        return buff;
    }
}
