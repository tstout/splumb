package splumb.core.logging;

import splumb.common.logging.*;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.*;

// todo - remove queueing impl from here...added to proper place in DBLogSink.
// This is now not injected as a singleton, let's try to inject the class name into this class when loading in the
// guice module.

public class HostLogger extends AbstractLogger {
    //public static String LOGGER_NAME = "splumb.host";

    private LogBus logBus;
    private LogConfig logConfig;
    private List<LogRecord> logQueue = newArrayList();
    private LogPublisher logImpl;
    private String callingClassName;

    @Inject
    public HostLogger(LogBus logBus, LogConfig logConfig) {
        this.logBus = logBus;
        this.logConfig = logConfig;
        //logImpl = new ActiveImpl();
    }

    @Override
    protected LogBus getLogBus() {
        return logBus;
    }

    @Override
    protected String getSource() {
        return callingClassName;
    }

    @Override
    protected boolean isRouteable(Level logLevel) {
        return logLevel.compareTo(logConfig.getLevel(callingClassName)) >= 0;
    }

    @Override
    public void info(String msg) {
        setCaller();
        super.info("%s", msg);
    }

    @Override
    public void info(String fmt, Object... parms) {
        setCaller();
        super.info(fmt, parms);
        //logImpl.info(fmt, parms);
    }

    @Override
    public void error(String fmt, Object... parms) {
        setCaller();
        super.error(fmt, parms);
    }

    @Override
    public void debug(String fmt, Object... parms) {
        setCaller();
        super.debug(fmt, parms);
    }

    private synchronized void setCaller() {
        if (callingClassName == null) {
            callingClassName = new Throwable().fillInStackTrace().getStackTrace()[2].getClassName();
        }
    }

//    class ActiveImpl extends AbstractLogger {
//
//        @Override
//        protected LogBus getLogBus() {
//            return logBus;
//        }
//
//        @Override
//        protected String getSource() {
//            return LOGGER_NAME;
//        }
//
//        @Override
//        protected boolean isRouteable(Level logLevel) {
//            return logLevel.compareTo(logConfig.getLevel(LOGGER_NAME)) >= 0;
//        }
//    }

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
