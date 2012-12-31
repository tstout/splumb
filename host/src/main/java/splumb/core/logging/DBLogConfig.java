package splumb.core.logging;

import com.google.common.base.Functions;
import com.google.common.eventbus.Subscribe;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBReader;
import splumb.common.logging.Level;
import splumb.common.logging.LogConfig;
import splumb.core.db.SplumbDB;
import splumb.core.events.HostDbTablesAvailableEvent;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.collect.Maps.*;

class DBLogConfig implements LogConfig {

    public Map<String, Level> settings = newHashMap();
    private SplumbDB db;

    @Inject
    public DBLogConfig(SplumbDB db) {
        this.db = db;
    }

    @Subscribe
    public void init(HostDbTablesAvailableEvent evt) {
        DBCommand cmd = db.createCommand();
        cmd.select(db.LogConfig.LOGGER, db.LogConfig.LOG_LEVEL);

        DBReader rdr = new DBReader();
        rdr.open(cmd, db.getConnection());

        try {
            while (rdr.moveNext()) {
                settings.put(rdr.getString(db.LogConfig.LOGGER),
                        Level.fromInt(rdr.getInt(db.LogConfig.LOG_LEVEL)));
            }
        }
        finally {
            rdr.close();
        }
    }


    @Override
    public Level getLevel(String logger) {
        return Functions.forMap(settings, Level.INFO).apply(logger);
    }
}
