package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.core.db.DBDriver;
import splumb.core.db.DataSet;
import splumb.core.db.SplumbDB;

import static com.google.common.collect.ImmutableSet.of;

/**
 * A Log consumer that writes to a database.
 */
public class DBLogSink {

    private SplumbDB db = new SplumbDB();
    private DBDriver driverFactory;
    private LogFormatter formatter = new LogFormatter();

    // TODO - move this somewhere else
    enum LogLevel {
      ERROR, INFO, DEBUG
    }

    @Inject
    public DBLogSink(DBDriver driverFactory) {
        this.driverFactory = driverFactory;
        db.open(driverFactory.getDriver(), driverFactory.getConnection());
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
                .insertInto(db.Log, driverFactory.getConnection());


//        DBRecord rec = new DBRecord();
//        rec.create(db.Log);
//        rec.setValue(db.Log.Level, level.ordinal());
//        rec.setValue(db.Log.DateTime, evt.timeStamp.get());
//        rec.setValue(db.Log.Msg, String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get()));
//        rec.update(driverFactory.getConnection());
    }
}
