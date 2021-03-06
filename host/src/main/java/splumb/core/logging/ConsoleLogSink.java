package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import splumb.common.logging.DebugLogEvent;
import splumb.common.logging.ErrorLogEvent;
import splumb.common.logging.InfoLogEvent;
import splumb.common.logging.LogEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;

// TODO - look into benefits of console vs out...
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
        return String.format("%s" + evt.fmt.get(),
                prepend(String.format("%s %s ", fmtTime(evt), evt.source.get()), evt.args.get()));
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
