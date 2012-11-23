package splumb.net.nio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static splumb.net.nio.NIOTestConstants.*;

class TestClient {
    private Client client;
    private CountDownLatch msgRx = new CountDownLatch(1);

    public TestClient(NetEndpoints endpoints) {

        client = endpoints.newTCPClient(NIOTestConstants.LOCAL_HOST,
                LOCAL_HOST_PORT,
                new MsgHandler() {
                    @Override
                    public void msgAvailable(Client sender, byte[] msg) {
                        msgRx.countDown();
                    }
                });
    }

    public Client socket() {
        return client;
    }

    public boolean waitForData() {
        try {
            return msgRx.await(MSG_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
