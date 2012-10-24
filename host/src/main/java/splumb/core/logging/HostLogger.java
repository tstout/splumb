package splumb.core.logging;

import splumb.common.logging.AbstractLogger;
import splumb.common.logging.Level;
import splumb.common.logging.LogBus;
import splumb.common.logging.LogConfig;

import javax.inject.Inject;

public class HostLogger extends AbstractLogger {
    private LogBus logBus;
    private LogConfig logConfig;
    private String callingClassName;

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
}
