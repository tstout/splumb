package splumb.common.logging;

public interface LogBus {
    void pub(LogEvent msg);

    void sub(Object listener);

    void unsub(Object listener);
}
