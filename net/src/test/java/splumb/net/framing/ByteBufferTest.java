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

//When you are done putting bytes into the buffer, you should flip it.
// A java.nio.Buffer distinguishes between 'capacity' and 'limit'. If you don't flip it, then limit and capacity are
// the same as the length of the array with which you initialized it. By flipping it, the limit will be set to the
// end of the data you've encoded, capacity will still be 1024.
// ByteBuffer#remaining looks at the delta between
// position and limit.
//
//
// Basic tests to verify my understanding of the ByteBuffer API
//
public class ByteBufferTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void initialPropertiesTest() {
        ByteBuffer buff = createFrame();
        buff.rewind();

        assertThat(buff.position(), is(0));
        assertThat(buff.limit(), is(10));
    }

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

        byte[] payload = new byte[TEST_PAYLOAD_1.length];

        assertThat(buff.getInt(), is(NativeFrameBuilder.MAGIC));
        assertThat(buff.getShort(), is((short) TEST_PAYLOAD_1.length));

        buff.get(payload);

        assertThat(Ints.fromByteArray(payload), is(PAYLOAD1));
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
        //buffFromNet.mark();
        assertThat(buff.remaining(), is(10 - 4));
    }


    ByteBuffer createFrame() {
        ByteBuffer buff = ByteBuffer.allocate(10);
        buff.put(NativeFrameBuilder.MAGIC_AS_ARRAY);
        buff.putShort((short) (TEST_PAYLOAD_1.length));
        buff.put(TEST_PAYLOAD_1);

        return buff;
    }
}
