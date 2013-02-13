package splumb.messaging;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.AbstractIdleService;
import splumb.common.logging.LogPublisher;
import splumb.core.db.SplumbDB;

import javax.inject.Inject;

public class MessagingService extends AbstractIdleService {

    LogPublisher logger;
    //Injector injector;
    BrokerConfig brokerConfig;
    BrokerService broker;

    @Inject
    public MessagingService(LogPublisher logger, SplumbDB db, EventBus bus) {
        this.logger = logger;
        //this.injector = injector;
        brokerConfig = new DbBrokerConfig(db);
        broker = new BrokerService(logger, bus);

        // create a child injector here and add any modules as necessary...
    }

    @Override
    protected void startUp() throws Exception {
        logger.info("Starting messaging service");
    }

    @Override
    protected void shutDown() throws Exception {
        logger.info("Messaging service shutdown");
    }
}
