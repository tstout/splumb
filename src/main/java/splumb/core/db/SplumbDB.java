package splumb.core.db;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import splumb.core.db.tables.Log;

import java.sql.Connection;

public class SplumbDB extends DBDatabase {

    public final Log Log = new Log(this);

    public SplumbDB create(DBDriver driver) {
        this.open(driver.getDriver(), driver.getConnection());

//        DBCommand cmd = createCommand();
//        cmd.select(Log.dateTime, Log.level, Log.msg);

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
