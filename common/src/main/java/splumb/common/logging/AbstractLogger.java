package splumb.common.logging;


public abstract class AbstractLogger implements LogPublisher {

    protected abstract LogBus getLogBus();

    protected abstract String getSource();

    protected abstract boolean isRouteable(Level logLevel);

    @Override
    public void info(String fmt, Object... parms) {
        if (isRouteable(Level.INFO)) {
            getLogBus().pub(new InfoLogEvent(getSource(), fmt, parms));
        }
    }

    @Override
    public void info(String msg) {
        if (isRouteable(Level.INFO)) {
            getLogBus().pub(new InfoLogEvent(getSource(), "%s", new Object[]{msg}));
        }
    }

    @Override
    public void error(String fmt, Object... parms) {
        if (isRouteable(Level.ERROR)) {
            getLogBus().pub(new ErrorLogEvent(getSource(), fmt, parms));
        }
    }

    @Override
    public void debug(String fmt, Object... parms) {
        if (isRouteable(Level.DEBUG)) {
            getLogBus().pub(new DebugLogEvent(getSource(), fmt, parms));
        }
    }
}
