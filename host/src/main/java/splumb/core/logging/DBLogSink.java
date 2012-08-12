package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.common.db.DataSet;
import splumb.common.logging.DebugLogEvent;
import splumb.common.logging.ErrorLogEvent;
import splumb.common.logging.InfoLogEvent;
import splumb.common.logging.LogEvent;
import splumb.core.db.SplumbDB;

import static com.google.common.collect.ImmutableSet.of;

/**
 * A Log consumer that writes to a database.
 */
 class DBLogSink {

    private SplumbDB db;


    // TODO - move this somewhere else
    enum LogLevel {
      ERROR, INFO, DEBUG
    }

    @Inject
    public DBLogSink(SplumbDB db) {
        this.db = db;
    }

    @Subscribe
    public void info(InfoLogEvent evt) {
        writeRecord(LogLevel.INFO, evt);
    }

    @Subscribe
    public void error(ErrorLogEvent evt) {
       writeRecord(LogLevel.ERROR, evt);
    }

    @Subscribe
    public void debug(DebugLogEvent evt) {
        writeRecord(LogLevel.DEBUG, evt);
    }

    private void writeRecord(LogLevel level, LogEvent evt) {

        new DataSet()
                .withColumns(of(db.Log.LEVEL, db.Log.DATE_TIME, db.Log.LOG_SOURCE, db.Log.MSG, db.Log.THREAD))
                .withValues(of(
                        level.ordinal() + 1,
                        evt.timeStamp.get(),
                        "unknown",
                        String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get()),
                        evt.thread.get()))
                .insertInto(db.Log, db.getConnection());
    }
}
