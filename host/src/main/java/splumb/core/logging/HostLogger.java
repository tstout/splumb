package splumb.core.logging;

import com.google.inject.Inject;
import splumb.common.logging.*;

import java.util.List;

import static com.google.common.collect.Lists.*;

// todo - remove queueing impl from here...added to property place in DBLogSink.

public class HostLogger extends AbstractLogger {
    public static String LOGGER_NAME = "splumb.host";

    private LogBus logBus;
    private LogConfig logConfig;
    private List<LogRecord> logQueue = newArrayList();
    private LogPublisher logImpl;


    @Inject
    public HostLogger(LogBus logBus, LogConfig logConfig) {
        this.logBus = logBus;
        this.logConfig = logConfig;
        logImpl = new ActiveImpl();
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

    @Override
    public void info(String msg) {
        logImpl.info("%s", msg);
    }

    @Override
    public void info(String fmt, Object... parms) {
        logImpl.info(fmt, parms);
    }

    @Override
    public void error(String fmt, Object... parms) {
        logImpl.error(fmt, parms);
    }

    @Override
    public void debug(String fmt, Object... parms) {
        logImpl.debug(fmt, parms);
    }

    class ActiveImpl extends AbstractLogger {

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

    class LogRecord {
        String fmt;
        Object[] args;
        Level level;

        public LogRecord(Level level, String fmt, Object[] args) {
            this.fmt = fmt;
            this.args = args;
            this.level = level;
        }
    }
}
