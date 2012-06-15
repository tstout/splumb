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
            level = addColumn("dateTime", DataType.DATETIME, 0, true);
            dateTime = addColumn("level", DataType.INTEGER, 0, true);
            msg = addColumn("msg", DataType.TEXT, 2048, true);

            setPrimaryKey(logId);
        }
    }


    // TODO -
    public static SplumbDB create() {
        SplumbDB db = new SplumbDB();
        DBDriverFactory h2cfg = new H2Driver();
        db.open(h2cfg.getDriver(), h2cfg.getConnection());

        DBCommand cmd = db.createCommand();
        cmd.select(db.Log.dateTime, db.Log.level, db.Log.msg);


        try {
            //
            // TODO...empire DB must provide a better way...
            //
            DBSQLScript script = new DBSQLScript();
            Connection conn = h2cfg.getConnection();
            db.getCreateDDLScript(h2cfg.getDriver(), script);
            script.run(h2cfg.getDriver(), conn, false);
            db.commit(conn);
        } catch (Exception e) {
          // table already created...
            //System.out.print(e.getMessage());
        }
        return db;
    }
}
