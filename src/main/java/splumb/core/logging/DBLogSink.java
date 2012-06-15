package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.core.db.DBDriverFactory;
import splumb.core.db.SplumbDB;

import java.util.Arrays;

/**
 * A Log consumer that writes to a database.
 */
public class DBLogSink {

    private SplumbDB db = new SplumbDB();
    private DBDriverFactory driverFactory;

    @Inject
    public DBLogSink(DBDriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    @Subscribe
    public void info(InfoLogEvent evt) {
        //System.out.printf("[INFO] %s\n", fmtMsg(evt));
    }

//    Object[] append(Object[] first, Object rest) {
//        Object[] copy = Arrays.copyOf(first, first.length + 1);
//        copy[first.length + 1] = rest;
//        return copy;
//    }
//
//    Object[] prepend(Object first, Object[] rest) {
//        Object[] dest = Arrays.copyOf(new Object[]{first}, rest.length + 1);
//        System.arraycopy(rest, 0, dest, 1, rest.length);
//        return dest;
//    }
//
//    String fmtMsg(LogEvent evt) {
//        return String.format("%s " + evt.fmt.get(),
//                prepend(fmtTime(evt), evt.args.get()));
//    }

    @Subscribe
    public void error(ErrorLogEvent evt) {
        //System.console().printf("%s: " + evt.fmt.get(), fmtTime(evt), evt.args.get());
    }

    @Subscribe
    public void debug(DebugLogEvent evt) {
        //System.out.printf("%s: " + evt.fmt.get(), fmtTime(evt), evt.args.get());
    }
}
