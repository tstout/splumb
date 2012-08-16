package splumb.common.logging;

public class DebugLogEvent extends LogEvent {
    public DebugLogEvent(String src, String fmt, Object[] args) {
        this.fmt.set(fmt);
        this.args.set(args);
        this.source.set(src);
    }
}