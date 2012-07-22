package splumb.core.logging;


import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import splumb.common.logging.LogBus;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class LogService extends AbstractIdleService {

    private LogBus bus;
    private List<?> logSinks;

    @Inject
    public LogService(LogBus bus) {
        this.bus = bus;

        logSinks = newArrayList(new ConsoleLogSink());
    }

    @Override
    protected void startUp() throws Exception {

        for (Object sink : logSinks) {
            bus.sub(sink);
        }
    }

    @Override
    protected void shutDown() throws Exception {
        for (Object sink : logSinks) {
            bus.unsub(sink);
        }
    }
}
