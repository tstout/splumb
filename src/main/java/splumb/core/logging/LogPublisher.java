package splumb.core.logging;

public interface LogPublisher {
    void info(String fmt, Object... parms);

    void info(String msg);

    void error(String fmt, Object... parms);

    void debug(String fmt, Object... parms);
}
