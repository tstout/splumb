package splumb.messaging;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import splumb.common.logging.LogPublisher;
import splumb.common.test.GuiceJUnitRunner;
import splumb.core.db.DBTestModule;
import splumb.core.db.SplumbDB;
import splumb.protobuf.BrokerMsg;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({DBTestModule.class, MessagingInjectModule.class, TestLogModule.class})
public class BrokerFTest {

    RemoteBroker remoteBroker;
    LocalBroker localBroker;
    MessageEndpoints endpoints;

    @Inject
    LogPublisher logger;
    @Inject
    SplumbDB db;

    @Before
    public void setup() {
        //
        // TODO - develop a cleaner way to init the SPlumbDB...
        //
        db.init(null);
        db.create();

        remoteBroker = new RemoteBroker(logger, new BrokerConfig(db));
        localBroker = new LocalBroker(logger);
        endpoints = new MessageEndpoints(logger, remoteBroker);
    }

    @Test
    public void sendTest() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        MessageSource source = endpoints
                .registerSink("test.q", new Sink(latch))
                .createSource("test.q");

        BrokerMsg.Msg msg = new MapMsgBuilder()
                .withDestination("test.q")
                .addInt32("test-key", 5)
                .build();

        source.send(msg);

        assertThat(latch.await(5, TimeUnit.SECONDS), is(true));
    }

    class Sink implements MessageSink {

        private final CountDownLatch latch;

        Sink(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void receive(BrokerMsg.Msg message) {
            latch.countDown();
        }
    }
}

