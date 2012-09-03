package splumb.core.logging;

import com.google.inject.Inject;
import splumb.common.logging.AbstractLogger;
import splumb.common.logging.Level;
import splumb.common.logging.LogBus;
import splumb.common.logging.LogConfig;


public class HostLogger extends AbstractLogger{
    public static String LOGGER_NAME = "host";

    private LogBus logBus;
    private LogConfig logConfig;

    @Inject
    public HostLogger(LogBus logBus, LogConfig logConfig) {
        this.logBus = logBus;
        this.logConfig = logConfig;
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
        return logLevel.compareTo(logConfig.getLevel(LOGGER_NAME)) >= 0;
    }
}
