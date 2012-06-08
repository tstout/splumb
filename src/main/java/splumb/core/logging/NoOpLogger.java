package splumb.core.logging;

public class NoOpLogger implements LogPublisher {
    @Override
    public void info(String fmt, Object... parms) {
    }

    @Override
    public void info(String msg) {
    }

    @Override
    public void error(String fmt, Object... parms) {
    }

    @Override
    public void debug(String fmt, Object... parms) {
    }
}
