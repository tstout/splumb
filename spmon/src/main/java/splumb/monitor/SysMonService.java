package splumb.monitor;

import splumb.common.logging.LogPublisher;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class SysMonService {
    final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private final LogPublisher logger;

    @Inject
    SysMonService(LogPublisher logger) {
        this.logger = logger;
    }

    void start() {
        scheduler.scheduleAtFixedRate(new MonitorTask(), 10, 10, TimeUnit.SECONDS);
    }

    void stop() {
        logger.info("Stopping SysMonService...");
        scheduler.shutdownNow();
        logger.info("SysMonService stopped");
    }

    class MonitorTask implements Runnable {

        @Override public void run() {
            logger.info("MonitorTask active");
        }
    }

}
