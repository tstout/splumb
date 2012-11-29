package splumb.net.nio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import splumb.common.logging.LogPublisher;

import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;
import static splumb.net.nio.NIOTestConstants.*;

public class BasicNIOTest {
    Server server;

    @Mock
    LogPublisher logger;

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
        NetEndpoints endpoints = new NetEndpoints(logger);

        final CountDownLatch msgRx = new CountDownLatch(2);

        server = endpoints.newTCPServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
                sender.send("howdy".getBytes());
            }
        });

        server.listen(LOCAL_HOST_PORT);

        Client client = endpoints.newTCPClient(LOCAL_HOST,
                LOCAL_HOST_PORT,
                new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
            }
        });

        client.send("hello".getBytes());
        assertThat(msgRx.await(10, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void clientBeforeServerTest() throws InterruptedException {
        NetEndpoints endpoints = new NetEndpoints(logger);

        final CountDownLatch msgRx = new CountDownLatch(2);

        Client client = endpoints.newTCPClient(LOCAL_HOST,
                LOCAL_HOST_PORT,
                new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
            }
        });

        client.send("hello".getBytes());

        server = endpoints.newTCPServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                msgRx.countDown();
                sender.send("howdy".getBytes());
            }
        });

        server.listen(LOCAL_HOST_PORT);
        assertThat(msgRx.await(10, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void twoClientTest() throws InterruptedException {

        NetEndpoints endpoints = new NetEndpoints(new ConsoleLog());

        server = endpoints.newTCPServer(new MsgHandler() {
            @Override
            public void msgAvailable(Client sender, byte[] msg) {
                sender.send(msg);
            }
        });

        server.listen(LOCAL_HOST_PORT);

        TestClient tc1 = new TestClient(endpoints);
        tc1.socket().send("Howdy1".getBytes());

        TestClient tc2 = new TestClient(endpoints);
        tc2.socket().send("Howdy2".getBytes());

        assertThat(tc1.waitForData(), is(true));
        assertThat(tc2.waitForData(), is(true));
    }

}

class ConsoleLog implements LogPublisher {

    @Override
    public void info(String fmt, Object... parms) {
        System.out.printf(new Formatter(Locale.getDefault()).format(fmt, parms).toString());
    }

    @Override
    public void info(String msg) {
       System.out.printf("%s\n", msg);
    }

    @Override
    public void error(String fmt, Object... parms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void debug(String fmt, Object... parms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}