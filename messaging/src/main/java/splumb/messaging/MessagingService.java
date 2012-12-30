package splumb.messaging;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Injector;
import splumb.common.logging.LogPublisher;

import javax.inject.Inject;

public class MessagingService extends AbstractIdleService {

    LogPublisher logger;
    Injector injector;

    @Inject
    public MessagingService(LogPublisher logger, Injector injector) {
        this.logger = logger;
        this.injector = injector;

        // create a child injector here and add any modules as necessary...
    }

    @Override
    protected void startUp() throws Exception {
        logger.info("Starting messaging service");
    }

    @Override
    protected void shutDown() throws Exception {
        logger.info("Stopping messaging service");
    }
}