package splumb.net.nio;

import com.google.common.net.InetAddresses;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import splumb.common.logging.LogPublisher;

import java.util.concurrent.CountDownLatch;

import static org.mockito.MockitoAnnotations.*;

public class BasicNIOTest {

    @Mock
    LogPublisher logger;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void msgTransferTest() throws InterruptedException {
        NetEndpoints endpoints = new NetEndpoints(logger);

        final CountDownLatch msgRx = new CountDownLatch(2);

        Server server = endpoints.newTCPServer(new MsgHandler() {
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
        msgRx.await();

    }



}
