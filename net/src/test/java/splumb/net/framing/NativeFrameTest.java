package splumb.net.framing;

import org.junit.Test;
import splumb.common.logging.ConsoleLogger;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;
import splumb.net.nio.Server;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Throwables.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.net.framing.FramingConstants.*;

public class NativeFrameTest {

    NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());
    Server server = endpoints.newTcpServer(new EchoServerHandler(), new NativeFramer());


    public NativeFrameTest() {
        server.listen(LISTEN_PORT);
    }

    class EchoServerHandler implements MsgHandler {

        @Override
        public void msgAvailable(Client sender, byte[] msg) {

            sender.send(new NativeFrameBuilder().withPayload(msg).build());
        }
    }

    class ClientHandler implements MsgHandler {
        private CountDownLatch latch = new CountDownLatch(1);

        private byte[] msg;

        @Override
        public void msgAvailable(Client sender, byte[] msg) {
            this.msg = msg;
            latch.countDown();
        }

        public byte[] getData() {
            return msg;
        }

        public boolean waitForRX() {
            boolean rc = false;

            try {
                rc = latch.await(3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                propagate(e);
            }

            return rc;
        }
    }

    @Test
    public void parseSimpleFrameTest() {
        ClientHandler handler = new ClientHandler();

        Client client = endpoints.clientBuilder()
                .withMsgHandler(handler)
                .withPort(8000)
                .build();

        client.send(new NativeFrameBuilder()
                .withPayload(TEST_PAYLOAD)
                .build().array());


        assertThat(handler.waitForRX(), is(true));
        assertThat(Arrays.equals(TEST_PAYLOAD, handler.getData()), is(true));


//        RxContext ctxt = new RxContext();
//        Listener listener = new Listener();
//
//        ctxt.frameListener = listener;
//        ctxt.buffFromNet = testFrame();//TEST_FRAME;
//
//        NativeFrameState.parse(ctxt);
//
//        assertThat(Arrays.equals(listener.payload.array(), TEST_PAYLOAD), is(true));
    }

    //@Test
    public void parseMultipleFrameTest() {
        //NativeFrameBuilder builder = new NativeFrameBuilder();

//        RxContext ctxt = new RxContext();
//        MultiFrameListener listener = new MultiFrameListener();
//
//        ctxt.frameListener = listener;
//        ByteBuffer twoFrames = ByteBuffer.allocate(testFrame().array().length * 2);
//
//        twoFrames.put(testFrame());
//        twoFrames.put(testFrame());
//        twoFrames.flip();
//        ctxt.buffFromNet = twoFrames;
//
//        NativeFrameState.parse(ctxt);
//        assertThat(listener.frameCount, is(2));
    }

    //@Test
    public void partialSingleFrameTest() {
//        RxContext ctxt = new RxContext();
//
//        ByteBuffer partialHeader = ByteBuffer.allocate(2);
//        partialHeader.putShort((short)0xDEAD);
//        partialHeader.flip();
//
//        MultiFrameListener listener = new MultiFrameListener();
//        ctxt.frameListener = listener;
//        ctxt.buffFromNet = partialHeader;
//
//
//        NativeFrameState.parse(ctxt);
//
//        ByteBuffer partialFrame = ByteBuffer.allocate(8);
//        partialFrame.putShort((short)0xBEEF);
//        partialFrame.putShort((short)4);
//        partialFrame.put(TEST_PAYLOAD);
//        partialFrame.flip();
//
//        ctxt.buffFromNet = partialFrame;
//        NativeFrameState.parse(ctxt);
//        assertThat(listener.frameCount, is(1));
    }
}

//class MultiFrameListener implements FrameListener {
//
//    int frameCount;
//
//    @Override
//    public void frameAvailable(Client client, ByteBuffer payload) {
//        frameCount++;
//        assertThat(Arrays.equals(payload.array(), TEST_PAYLOAD), is(true));
//    }
//}
//
//class Listener implements FrameListener {
//
//    public ByteBuffer payload;
//
//    @Override
//    public void frameAvailable(Client client, ByteBuffer payload) {
//        this.payload = payload;
//    }
//}