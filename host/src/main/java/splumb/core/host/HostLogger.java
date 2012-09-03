package splumb.core.host;

import com.google.inject.Inject;
import splumb.common.logging.AbstractLogger;
import splumb.common.logging.Level;
import splumb.common.logging.LogBus;


public class HostLogger extends AbstractLogger{
    public static String LOGGER_NAME = "host";

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
        return LOGGER_NAME;
    }

    @Override
    protected boolean isRouteable(Level logLevel) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
