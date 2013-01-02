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

        BrokerMsg.MapMsg msg = new MapMsgBuilder()
                .addInt32("test-key", 5)
                .build();
    }
}

