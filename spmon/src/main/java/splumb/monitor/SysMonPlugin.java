package splumb.monitor;

import com.google.common.util.concurrent.AbstractIdleService;
import splumb.common.logging.LogPublisher;

import javax.inject.Inject;

public class SysMonPlugin extends AbstractIdleService {
    private SysMonService service;

    @Inject
    SysMonPlugin(LogPublisher logger) {
        service = new SysMonService(logger);
    }

    @Override protected void startUp() throws Exception {
        service.start();
    }

    @Override protected void shutDown() throws Exception {
        service.stop();
    }
}
