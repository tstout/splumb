package splumb.core.host;

import com.google.inject.Inject;
import splumb.common.logging.AbstractLogger;
import splumb.common.logging.LogBus;


public class HostLogger extends AbstractLogger{

    private LogBus logBus;

    @Inject
    public HostLogger(LogBus logBus) {
        this.logBus = logBus;
    }

    @Override
    protected LogBus getLogBus() {
        return logBus;
    }

    @Override
    protected String getSource() {
        return "host";
    }
}
