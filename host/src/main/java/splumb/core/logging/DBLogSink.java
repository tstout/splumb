package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.common.db.DataSet;
import splumb.core.db.SplumbDB;

import static com.google.common.collect.ImmutableSet.of;

/**
 * A Log consumer that writes to a database.
 * todo - probably don't need this to be public....
 */
public class DBLogSink {

    private SplumbDB db;
    private LogFormatter formatter = new LogFormatter();

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
                .withColumns(of(db.Log.Level, db.Log.DateTime, db.Log.Msg))
                .withValues(of(
                        level.ordinal(),
                        evt.timeStamp.get(),
                        String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get())))
                .insertInto(db.Log, db.getConnection());


//        DBRecord rec = new DBRecord();
//        rec.create(db.Log);
//        rec.setValue(db.Log.Level, level.ordinal());
//        rec.setValue(db.Log.DateTime, evt.timeStamp.get());
//        rec.setValue(db.Log.Msg, String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get()));
//        rec.update(driverFactory.getConnection());
    }
}
