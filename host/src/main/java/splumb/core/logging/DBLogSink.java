package splumb.core.logging;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import db.io.operations.UpdateBuilder;
import splumb.common.logging.DebugLogEvent;
import splumb.common.logging.ErrorLogEvent;
import splumb.common.logging.InfoLogEvent;
import splumb.common.logging.Level;
import splumb.common.logging.LogEvent;
import splumb.core.db.SplumbDB;
import splumb.core.events.HostDbTablesAvailableEvent;

import java.util.List;

import static com.google.common.collect.Lists.*;
import static java.lang.String.*;

/**
 * A Log consumer that writes to a database.
 */
 class DBLogSink {
    private final Object lock = new Object();
    private List<LogRecord> logQueue = newArrayList();
    private LogImpl logImpl = new QueueImpl();
    private UpdateBuilder updateBuilder;

    @Inject
    public DBLogSink(SplumbDB db) {
        updateBuilder = new UpdateBuilder()
                .withCreds(db.credentials())
                .withDb(db.database());
    }

    @Subscribe
    public void info(InfoLogEvent evt) {
        logImpl.writeRecord(Level.INFO, evt);
    }

    @Subscribe
    public void error(ErrorLogEvent evt) {
       logImpl.writeRecord(Level.ERROR, evt);
    }

    @Subscribe
    public void debug(DebugLogEvent evt) {
        logImpl.writeRecord(Level.DEBUG, evt);
    }

    class LogImpl {
        void writeRecord(Level level, LogEvent evt) {
            DBLogSink.this.writeRecord(level, evt);
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
        synchronized (lock) {
            logImpl = new LogImpl();

            for (LogRecord log : logQueue) {
                logImpl.writeRecord(log.level, log.evt);
            }

            logQueue.clear();
        }
    }
    // TODO - this will be externalized to a resource file...
    private static final String INSERT_SQL =
            "insert into splumb.Logs (level, when, logger, msg, thread) values (?, ?, ?, ?, ?)";

    private void writeRecord(Level level, LogEvent evt) {

        updateBuilder.addOp(INSERT_SQL,
                level.name(),
                evt.timeStamp.get(),
                evt.source.get(),
                format(evt.fmt.get() == null ? "%s" : evt.fmt.get(),
                        evt.args.get()),
                evt.thread.get())
                .build()
                .update();
    }
}
