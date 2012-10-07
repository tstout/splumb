package splumb.cron;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Injector;
import splumb.common.logging.LogPublisher;

import javax.inject.Inject;

public class CronService extends AbstractIdleService {
    LogPublisher logger;
    Injector injector;

    @Inject
    public CronService(LogPublisher logger, Injector injector) {
        this.logger = logger;
        this.injector = injector;

        // create a child injector here and add any modules as necessary...

    }

    @Override
    protected void startUp() throws Exception {
        logger.info("Starting cron service with injector");

    }

    @Override
    protected void shutDown() throws Exception {
        logger.info("cron service shutdown");
    }
}
