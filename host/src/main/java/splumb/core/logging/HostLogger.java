package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.common.logging.*;
import splumb.core.events.HostDbTablesAvailableEvent;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static splumb.common.logging.Level.*;


public class HostLogger extends AbstractLogger {
    public static String LOGGER_NAME = "host";

    private LogBus logBus;
    private LogConfig logConfig;
    private List<LogRecord> logQueue = newArrayList();
    private LogPublisher logImpl;

    @Inject
    public HostLogger(LogBus logBus, LogConfig logConfig) {
        this.logBus = logBus;
        this.logConfig = logConfig;
        logImpl = new QueueingImpl();
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

    @Subscribe
    public void dbAvailable(HostDbTablesAvailableEvent hostDbTablesAvailableEvent) {
        logImpl = new ActiveImpl();

        for (LogRecord log : logQueue) {
            switch (log.level) {
                case INFO:
                    logImpl.info(log.fmt, log.args);
                    break;
                case ERROR:
                    logImpl.error(log.fmt, log.args);
                    break;
                case DEBUG:
                    logImpl.debug(log.fmt, log.args);
                    break;
            }
        }

        logQueue.clear();
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

    class QueueingImpl implements LogPublisher {

        @Override
        public void info(String fmt, Object... parms) {
            logQueue.add(new LogRecord(INFO, fmt, parms));
        }

        @Override
        public void info(String msg) {
            logQueue.add(new LogRecord(INFO, "%s", new Object[]{msg}));
        }

        @Override
        public void error(String fmt, Object... parms) {
            logQueue.add(new LogRecord(ERROR, fmt, parms));
        }

        @Override
        public void debug(String fmt, Object... parms) {
            logQueue.add(new LogRecord(DEBUG, fmt, parms));
        }
    }
}
