package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import splumb.common.db.DataSet;
import splumb.common.logging.*;
import splumb.core.db.SplumbDB;
import splumb.core.events.HostDbTablesAvailableEvent;

import java.sql.Connection;
import java.util.List;

import static com.google.common.collect.ImmutableSet.*;
import static com.google.common.collect.Lists.*;

/**
 * A Log consumer that writes to a database.
 */
 class DBLogSink {
    private List<LogRecord> logQueue = newArrayList();
    private SplumbDB db;
    private LogImpl logImpl = new QueueImpl();
    Connection conn;

    @Inject
    public DBLogSink(SplumbDB db) {
        this.db = db;
        conn = db.getConnection();
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

    class LogImpl {
        void writeRecord(Level level, LogEvent evt) {
            writeRecord(level, evt);
        }
    }

    class QueueImpl extends LogImpl {
        @Override
        void writeRecord(Level level, LogEvent evt) {
            logQueue.add(new LogRecord(level, evt));
        }
    }

    class LogRecord {
        Level level;
        LogEvent evt;


        public LogRecord(Level level, LogEvent evt) {
            this.evt = evt;
            this.level = level;
        }
    }

    @Subscribe
    public void dbAvailable(HostDbTablesAvailableEvent hostDbTablesAvailableEvent) {
        logImpl = new LogImpl();

        for (LogRecord log : logQueue) {
          logImpl.writeRecord(log.level, log.evt);
        }

        logQueue.clear();
    }

    private void writeRecord(Level level, LogEvent evt) {

        new DataSet()
                .withColumns(of(db.Log.LEVEL, db.Log.DATE_TIME, db.Log.LOGGER, db.Log.MSG, db.Log.THREAD))
                .withValues(of(
                        level.ordinal() + 1,
                        evt.timeStamp.get(),
                        evt.source.get(),
                        String.format(evt.fmt.get() == null ? "%s" : evt.fmt.get(), evt.args.get()),
                        evt.thread.get()))
                .insertInto(db.Log, conn);
    }
}
