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

import static com.google.common.base.Throwables.propagate;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({DBTestModule.class, MessagingInjectModule.class, TestLogModule.class})
public class BasicMessagingTest {

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
    public void sendTest() {
        MessageSource source = endpoints.createSource(MessagingConstants.ADMIN_REQ_Q);

        BrokerMsg.Msg msg = new MapMsgBuilder()
                .withDestination(MessagingConstants.ADMIN_REQ_Q)
                .addInt32("test-key", 5)
                .build();

        source.send(msg);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            propagate(e);
        }
    }
}

