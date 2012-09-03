package splumb.core.logging;

import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBReader;
import org.apache.empire.db.DBRecord;
import splumb.core.db.SplumbDB;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

class LogConfig {
    public Map<String, Integer> settings = newHashMap();

    @Inject
    public LogConfig(SplumbDB db) {
        DBCommand cmd = db.createCommand();
        cmd.select(db.Log.LEVEL, db.Log.MSG);
        DBReader rdr = new DBReader();
        rdr.open(cmd, db.getConnection());
    }


    class ConfigRecord extends DBRecord {

    }
}
