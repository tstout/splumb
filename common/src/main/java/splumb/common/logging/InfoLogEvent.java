package splumb.common.logging;

// TODO -  make varargs...
public class InfoLogEvent extends LogEvent {
    public InfoLogEvent(String src, String fmt, Object[] args) {
        this.fmt.set(fmt);
        this.args.set(args);
        this.source.set(src);
    }
}
