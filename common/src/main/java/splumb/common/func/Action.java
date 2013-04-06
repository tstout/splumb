package splumb.common.func;

public interface Action<I> {
    void invoke(I input);
}
