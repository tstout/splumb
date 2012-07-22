package splumb.common.logging;

public class DebugLogEvent extends LogEvent {
    public DebugLogEvent(String fmt, Object[] args) {
        this.fmt.set(fmt);
        this.args.set(args);
    }
}