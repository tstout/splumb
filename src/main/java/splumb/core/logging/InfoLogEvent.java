package splumb.core.logging;

public class InfoLogEvent extends LogEvent {
    public InfoLogEvent(String fmt, Object[] args) {
        this.fmt.set(fmt);
        this.args.set(args);
    }
}
