package splumb.core.logging;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import db.io.operations.QueryBuilder;
import splumb.common.logging.Level;
import splumb.common.logging.LogConfig;
import splumb.core.db.SplumbDB;
import splumb.core.db.tables.LogConfigRecord;
import splumb.core.events.HostDbTablesAvailableEvent;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

class DBLogConfig implements LogConfig {
    private final static LogConfigRecord DEF_LOG_RECORD = new LogConfigRecord() {
        public String logger() { return null; }
        public String level() { return Level.INFO.toString(); }
    };

    public Map<String, LogConfigRecord> settings = newHashMap();
    private SplumbDB db;

    @Inject
    public DBLogConfig(SplumbDB db) {
        this.db = db;
    }

    @Subscribe
    public void init(HostDbTablesAvailableEvent evt) {
        Collection<LogConfigRecord> config = new QueryBuilder()
                .withCreds(db.credentials())
                .withDb(db.database())
                .build()
                .execute(LogConfigRecord.class,
                        "select logger, level from splumb.logconfig");

        settings = Maps.uniqueIndex(config, new Function<LogConfigRecord, String>() {
            public String apply(LogConfigRecord input) {
                return input.logger();
            }
        });
    }


    @Override
    public Level getLevel(String logger) {
        return Level.fromStr(Functions
                .forMap(settings, DEF_LOG_RECORD)
                .apply(logger)
                .level());
    }
}
