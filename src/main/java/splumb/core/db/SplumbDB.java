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

    public static SplumbDB create() {
        SplumbDB db = new SplumbDB();
        H2Config h2cfg = new H2Config();
        db.open(h2cfg.getDriver(), h2cfg.getConnection());

        //
        // Check whether DB exists
        //
        DBCommand cmd = db.createCommand();

        cmd.select(db.Log.count());

        try {
            //
            // TODO...empire DB must provide a better way...
            //
            DBSQLScript script = new DBSQLScript();
            Connection conn = h2cfg.getConnection();
            db.getCreateDDLScript(h2cfg.getDriver(), script);
            // Show DDL Statement

            System.out.println(script.toString());
            // Execute Script
            script.run(h2cfg.getDriver(), conn, false);
            // Commit
            db.commit(conn);
        } catch (Exception e) {
          // table already created...
        }


        return db;
    }
}
