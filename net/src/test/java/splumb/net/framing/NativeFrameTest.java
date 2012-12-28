package splumb.net.framing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import splumb.common.logging.ConsoleLogger;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;
import splumb.net.nio.Server;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Lists.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.net.framing.FramingConstants.*;

public class NativeFrameTest {

    NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());
    Server server;

    @Before
    public void setup() {
        server = endpoints.newTcpServer(new EchoServerHandler(), new NativeFramer());
        server.listen(LISTEN_PORT);
    }

    @After
    public void after() {
        server.close();
    }

    class EchoServerHandler implements MsgHandler {

        @Override
        public void msgAvailable(Client sender, byte[] msg) {

            sender.send(new NativeFrameBuilder().withPayload(msg).build());
        }
    }

    class ClientHandler implements MsgHandler {

        private CountDownLatch latch;
        private List<byte[]> messages = newArrayList();

        ClientHandler(int expectedMsgCount) {
            latch = new CountDownLatch(expectedMsgCount);
        }

        @Override
        public void msgAvailable(Client sender, byte[] msg) {
            messages.add(msg);
            latch.countDown();
        }

        public byte[] getData(int i) {
            return messages.get(i);
        }

        public boolean waitForRX() {
            boolean rc = false;

            try {
                rc = latch.await(4000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                propagate(e);
            }

            return rc;
        }
    }

    @Test
    public void parseSimpleFrameTest() {
        ClientHandler handler = new ClientHandler(1);

        Client client = endpoints.clientBuilder()
                .withMsgHandler(handler)
                .withPort(8000)
                .build();

        client.send(new NativeFrameBuilder()
                .withPayload(TEST_PAYLOAD_1)
                .build().array());


        assertThat(handler.waitForRX(), is(true));
        assertThat(Arrays.equals(TEST_PAYLOAD_1, handler.getData(0)), is(true));
    }

    @Test
    public void parseMultipleFrameTest() {
        ClientHandler handler = new ClientHandler(2);

        Client client = endpoints.clientBuilder()
                .withMsgHandler(handler)
                .withPort(8000)
                .build();

        client.send(new NativeFrameBuilder().withPayload(TEST_PAYLOAD_1).build().array());
        client.send(new NativeFrameBuilder().withPayload(TEST_PAYLOAD_2).build().array());

        assertThat(handler.waitForRX(), is(true));
        assertThat(Arrays.equals(TEST_PAYLOAD_1, handler.getData(0)), is(true));
        assertThat(Arrays.equals(TEST_PAYLOAD_2, handler.getData(1)), is(true));
    }

    @Test
    public void partialSingleFrameTest() {
        ClientHandler handler = new ClientHandler(1);

        Client client = endpoints.clientBuilder()
                .withMsgHandler(handler)
                .withPort(8000)
                .build();


        ByteBuffer partialHeader = ByteBuffer.allocate(2);
        partialHeader.putShort((short)0xDEAD);
        partialHeader.flip();

        client.send(partialHeader);

        ByteBuffer partialFrame = ByteBuffer.allocate(8);
        partialFrame.putShort((short)0xBEEF);
        partialFrame.putShort((short)4);
        partialFrame.put(TEST_PAYLOAD_1);
        partialFrame.flip();

        client.send(partialFrame);

        assertThat(handler.waitForRX(), is(true));
        assertThat(Arrays.equals(TEST_PAYLOAD_1, handler.getData(0)), is(true));
    }
}