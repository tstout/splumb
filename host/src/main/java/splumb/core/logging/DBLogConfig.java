package splumb.core.logging;

import com.google.common.base.Functions;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBReader;
import splumb.common.logging.Level;
import splumb.common.logging.LogConfig;
import splumb.core.db.SplumbDB;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

class DBLogConfig implements LogConfig {

    public Map<String, Level> settings = newHashMap();

    @Inject
    public DBLogConfig(SplumbDB db) {
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
        return Functions.forMap(settings, Level.ERROR).apply(logger);
    }
}
