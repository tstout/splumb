package splumb.common.logging;

import splumb.common.logging.LogEvent;

import java.text.SimpleDateFormat;
import java.util.Arrays;

class  LogFormatter {
    private SimpleDateFormat dateFmt =
            new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");

    public String fmtTime(LogEvent evt) {
        return dateFmt.format(evt.timeStamp.get());
    }

    public String format(LogEvent evt) {
        //
        // TODO - this probably is not very performant...
        //
        return String.format("%s " + evt.fmt.get(),
                prepend(fmtTime(evt), evt.args.get()));
    }

    public String formatWithoutTime(LogEvent evt) {
        //
        // TODO - this probably is not very performant...
        //
        return String.format("%s " + evt.fmt.get(),
                prepend(fmtTime(evt), evt.args.get()));
    }

    public Object[] append(Object[] first, Object rest) {
        Object[] copy = Arrays.copyOf(first, first.length + 1);
        copy[first.length + 1] = rest;
        return copy;
    }

    public Object[] prepend(Object first, Object[] rest) {
        Object[] dest = Arrays.copyOf(new Object[]{first}, rest.length + 1);
        System.arraycopy(rest, 0, dest, 1, rest.length);
        return dest;
    }

}
