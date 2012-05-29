package splumb.core.logging;

public interface LogBus {
    void pub(Object msg);

    void sub(Object listener);
}
