package splumb.core.logging;

public interface LogBus {
    void pub(LogEvent msg);

    void sub(Object listener);
}
