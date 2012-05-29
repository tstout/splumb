package splumb.core.logging;

public class ErrorLogEvent extends LogEvent {
    public ErrorLogEvent(String fmt, Object[] args) {
        this.fmt.set(fmt);
        this.args.set(args);
    }
}
