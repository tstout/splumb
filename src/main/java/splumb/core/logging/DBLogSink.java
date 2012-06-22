package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.apache.empire.db.DBRecord;
import splumb.core.db.DBDriver;
import splumb.core.db.SplumbDB;

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
        DBRecord rec = new DBRecord();
        rec.create(db.Log);
        rec.setValue(db.Log.level, level.ordinal());
        rec.setValue(db.Log.dateTime, evt.timeStamp.get());
        rec.setValue(db.Log.msg, String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get()));
        rec.update(driverFactory.getConnection());
    }
}
