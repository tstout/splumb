package splumb.core.host;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.google.common.eventbus.Subscribe;
import splumb.core.logging.DebugLogEvent;
import splumb.core.logging.ErrorLogEvent;
import splumb.core.logging.InfoLogEvent;
import splumb.core.logging.LogEvent;

// TODO - look into benefits of console vs out...
// TODO - does this belong in host?
class ConsoleLogSink {

    SimpleDateFormat dateFmt =
            new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");

    @Subscribe
    public void info(InfoLogEvent evt) {
        System.out.printf("[INFO] %s\n", fmtMsg(evt));
    }

    Object[] append(Object[] first, Object rest) {
        Object[] copy = Arrays.copyOf(first, first.length + 1);
        copy[first.length + 1] = rest;
        return copy;
    }

    Object[] prepend(Object first, Object[] rest) {
        Object[] dest = Arrays.copyOf(new Object[]{first}, rest.length + 1);
        System.arraycopy(rest, 0, dest, 1, rest.length);
        return dest;
    }

    String fmtMsg(LogEvent evt) {
        return String.format("%s " + evt.fmt.get(),
                prepend(fmtTime(evt), evt.args.get()));
    }

    @Subscribe
    public void error(ErrorLogEvent evt) {
        System.console().printf("%s: " + evt.fmt.get(), fmtTime(evt), evt.args.get());
    }

    @Subscribe
    public void debug(DebugLogEvent evt) {
        System.out.printf("%s: " + evt.fmt.get(), fmtTime(evt), evt.args.get());
    }

    String fmtTime(LogEvent evt) {
        return dateFmt.format(evt.timeStamp.get());
    }
}
