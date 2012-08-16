package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.common.db.DataSet;
import splumb.common.logging.*;
import splumb.core.db.SplumbDB;

import static com.google.common.collect.ImmutableSet.of;

/**
 * A Log consumer that writes to a database.
 */
 class DBLogSink {

    private SplumbDB db;

    @Inject
    public DBLogSink(SplumbDB db) {
        this.db = db;
    }

    @Subscribe
    public void info(InfoLogEvent evt) {
        writeRecord(Level.INFO, evt);
    }

    @Subscribe
    public void error(ErrorLogEvent evt) {
       writeRecord(Level.ERROR, evt);
    }

    @Subscribe
    public void debug(DebugLogEvent evt) {
        writeRecord(Level.DEBUG, evt);
    }

    private void writeRecord(Level level, LogEvent evt) {

        new DataSet()
                .withColumns(of(db.Log.LEVEL, db.Log.DATE_TIME, db.Log.LOG_SOURCE, db.Log.MSG, db.Log.THREAD))
                .withValues(of(
                        level.ordinal() + 1,
                        evt.timeStamp.get(),
                        evt.source.get(),
                        String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get()),
                        evt.thread.get()))
                .insertInto(db.Log, db.getConnection());
    }
}
