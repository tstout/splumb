package splumb.common.logging;

public interface LogPublisher {
    void info(String fmt, Object... parms);

    // TODO - this method not necessary
    void info(String msg);

    void error(String fmt, Object... parms);

    void debug(String fmt, Object... parms);
}
