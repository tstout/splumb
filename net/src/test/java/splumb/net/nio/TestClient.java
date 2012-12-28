package splumb.net.nio;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static splumb.net.nio.NIOTestConstants.*;

class TestClient implements MsgHandler {
    private Client client;
    private CountDownLatch msgRx = new CountDownLatch(1);
    private byte[] msg;
    private String name;

    public TestClient(NetEndpoints endpoints, String name) {
        this.name = name;

        client = endpoints.newTcpClient(NIOTestConstants.LOCAL_HOST,
                LOCAL_HOST_PORT,
                this,
                new TestFramer());
    }

    public Client socket() {
        return client;
    }

    public byte[] data() {
        return msg;
    }

    public boolean waitForData() {
        try {
            return msgRx.await(MSG_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        this.msg = msg;
        msgRx.countDown();
    }
}
