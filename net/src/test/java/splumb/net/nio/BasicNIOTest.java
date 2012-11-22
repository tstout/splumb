package splumb.net.nio;

import com.google.common.net.InetAddresses;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import splumb.common.logging.LogPublisher;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;

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
            public void msgAvailable(NetEndpoint sender, byte[] msg) {
                msgRx.countDown();
                ((Client)sender).send("howdy".getBytes());
            }
        });

        server.listen(8000);

        Client client = endpoints.newTCPClient(InetAddresses.forString("127.0.0.1"), 8000, new MsgHandler() {
            @Override
            public void msgAvailable(NetEndpoint sender, byte[] msg) {
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

        Client client = endpoints.newTCPClient(InetAddresses.forString("127.0.0.1"), 8000, new MsgHandler() {
            @Override
            public void msgAvailable(NetEndpoint sender, byte[] msg) {
                msgRx.countDown();
            }
        });

        client.send("hello".getBytes());

        server = endpoints.newTCPServer(new MsgHandler() {
            @Override
            public void msgAvailable(NetEndpoint sender, byte[] msg) {
                msgRx.countDown();
                ((Client)sender).send("howdy".getBytes());
            }
        });

        server.listen(8000);
        assertThat(msgRx.await(10, TimeUnit.SECONDS), is(true));
    }

}
