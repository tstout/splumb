package splumb.net.nio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import splumb.common.logging.ConsoleLogger;
import splumb.net.framing.Framer;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;
import static splumb.net.nio.NIOTestConstants.*;

public class BasicNIOTest {
    Server server;
    Framer framer = new TestFramer();

    @Before
    public void setup() {
        initMocks(this);
    }

    @After
    public void teardown() {
        server.close();
    }

    @Test
    public void serverBeforeClientTest() throws InterruptedException {
        NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());

        final CountDownLatch msgRx = new CountDownLatch(2);

        server = endpoints.newTcpServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
                sender.send("howdy".getBytes());
            }
        },
                framer);

        server.listen(LOCAL_HOST_PORT);

        Client client = endpoints.newTcpClient(LOCAL_HOST,
                LOCAL_HOST_PORT,
                new MsgHandler() {
                    @Override
                    public void msgAvailable(Client sender, byte[] msg) {
                        msgRx.countDown();
                    }
                }, framer);

        client.send("hello".getBytes());
        assertThat(msgRx.await(10, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void clientBeforeServerTest() throws InterruptedException {
        NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());

        final CountDownLatch msgRx = new CountDownLatch(2);

        Client client = endpoints.newTcpClient(LOCAL_HOST,
                LOCAL_HOST_PORT,
                new MsgHandler() {
                    @Override
                    public void msgAvailable(Client sender, byte[] msg) {
                        msgRx.countDown();
                    }
                }, framer);

        client.send("hello".getBytes());

        server = endpoints.newTcpServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
                sender.send("howdy".getBytes());
            }
        }, framer);

        server.listen(LOCAL_HOST_PORT);
        assertThat(msgRx.await(10, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void twoClientTest() throws InterruptedException {

        NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());

        server = endpoints.newTcpServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                sender.send(msg);
            }
        }, framer);

        server.listen(LOCAL_HOST_PORT);

        TestClient tc1 = new TestClient(endpoints, "one");
        tc1.socket().send("Howdy1".getBytes());

        TestClient tc2 = new TestClient(endpoints, "two");
        tc2.socket().send("Howdy2".getBytes());

        assertThat(tc1.waitForData(), is(true));
        assertThat(tc2.waitForData(), is(true));
    }

    @Test
    public void threeClientTest() {
        NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());

        server = endpoints.newTcpServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                sender.send(msg);
            }
        }, framer);

        server.listen(LOCAL_HOST_PORT);

        TestClient tc1 = new TestClient(endpoints, "one");
        tc1.socket().send("Howdy1".getBytes());

        TestClient tc2 = new TestClient(endpoints, "two");
        tc2.socket().send("Howdy2".getBytes());

        TestClient tc3 = new TestClient(endpoints, "three");
        tc3.socket().send("Howdy3".getBytes());

        assertThat(tc1.waitForData(), is(true));
        assertThat(Arrays.equals(tc1.data(), "Howdy1".getBytes()), is(true));
        assertThat(tc2.waitForData(), is(true));
        assertThat(Arrays.equals(tc2.data(), "Howdy2".getBytes()), is(true));
        assertThat(tc3.waitForData(), is(true));
        assertThat(Arrays.equals(tc3.data(), "Howdy3".getBytes()), is(true));
    }

    @Test
    public void clientBeforeServerWithDelay() throws InterruptedException {
        NetEndpoints endpoints = new NetEndpoints(new ConsoleLogger());

        final CountDownLatch msgRx = new CountDownLatch(2);

        Client client = endpoints.newTcpClient(LOCAL_HOST,
                LOCAL_HOST_PORT,
                new MsgHandler() {
                    @Override
                    public void msgAvailable(Client sender, byte[] msg) {
                        msgRx.countDown();
                    }
                }, framer);

        client.send("hello".getBytes());

        Thread.sleep(1500);

        server = endpoints.newTcpServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
                sender.send("howdy".getBytes());
            }
        }, framer);

        server.listen(LOCAL_HOST_PORT);
        assertThat(msgRx.await(10, TimeUnit.SECONDS), is(true));

    }
}

