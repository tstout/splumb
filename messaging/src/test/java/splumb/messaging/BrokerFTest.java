package splumb.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import splumb.common.test.GuiceJUnitRunner;
import splumb.core.db.SplumbDB;
import splumb.protobuf.BrokerMsg;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({BrokerTestModule.class})
public class BrokerFTest {

    @Inject BrokerClient brokerClient;
    @Inject BrokerService brokerService;
    @Inject MessageEndpoints endpoints;
    @Inject SplumbDB db;

    @Test
    public void singleMsgTest() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        MessageSource source = endpoints
                .createSource("test.q");

        BrokerMsg.Msg msg = new MapMsgBuilder()
                .withDestination("test.q")
                .add("test-key2", 8)
                .add("test-key", "hello")
                .build();

        source.send(msg);

        endpoints.registerSink("test.q", new Sink(latch));

        assertThat(latch.await(5, TimeUnit.SECONDS), is(true));
    }

    class Sink implements MessageSink {

        private final CountDownLatch latch;

        Sink(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void receive(BrokerMsg.Msg message) {
            MapMsgSelector mp = new MapMsgSelector(message.getMapMsg());

            assertThat(mp.get("test-key", String.class), is("hello"));
            assertThat(mp.get("test-key2", Integer.class), is(8));
            latch.countDown();
        }
    }
}

