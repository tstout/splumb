package splumb.core.db;

import org.apache.empire.data.DataType;
import org.apache.empire.db.*;

import java.sql.Connection;

public class SplumbDB extends DBDatabase {

    public final Log Log = new Log(this);

    public static class Log extends DBTable {

        public final DBTableColumn logId;
        public final DBTableColumn level;
        public final DBTableColumn dateTime;
        public final DBTableColumn msg;

        public Log(DBDatabase db) {
            super("Log", db);

            logId = addColumn("logId", DataType.AUTOINC, 0, true, "logIdSequence");
            dateTime = addColumn("dateTime", DataType.DATETIME, 0, true);
            level = addColumn("level", DataType.INTEGER, 0, true);
            msg = addColumn("msg", DataType.TEXT, 2048, true);

            setPrimaryKey(logId);
        }
    }

    public SplumbDB create(DBDriver driver) {
        this.open(driver.getDriver(), driver.getConnection());

        DBCommand cmd = createCommand();
        cmd.select(Log.dateTime, Log.level, Log.msg);

        try {
            //
            // TODO...empire DB must provide a better way...
            //
            DBSQLScript script = new DBSQLScript();
            Connection conn = driver.getConnection();
            getCreateDDLScript(driver.getDriver(), script);
            script.run(driver.getDriver(), conn, false);
            commit(conn);
        } catch (Exception e) {
          // table already created...
        }
        return this;
    }
}
