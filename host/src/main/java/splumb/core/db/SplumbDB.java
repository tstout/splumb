package splumb.core.db;


import com.google.inject.Inject;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.DataSet;
import splumb.core.db.tables.Log;
import splumb.core.db.tables.LogLevel;

import java.sql.Connection;

import static com.google.common.collect.ImmutableSet.of;

public class SplumbDB extends DBDatabase {

    public final Log Log = new Log(this);
    public final LogLevel LogLevel = new LogLevel(this);

    private DBDriver driver;

    @Inject
    public SplumbDB(DBDriver driver) {
        this.driver = driver;
        addRelation(Log.Level.referenceOn(LogLevel.LogLevelId));
        this.open(driver.getDriver(), driver.getConnection());
    }

    public Connection getConnection() {
        return driver.getConnection();
    }

    public SplumbDB create() {
//        this.open(driver.getDriver(), driver.getConnection());
//        this.driver = driver;

//        DBSQLScript dropScript = new DBSQLScript();
//
//        Connection dropConn = driver.getConnection();
//        driver.getDriver().getDDLScript(DBCmdType.DROP, Log, dropScript);
//        dropScript.run(driver.getDriver(), getConnection(), false);
//        commit(dropConn);

        try {
            //
            // TODO...empire DB must provide a better way...
            //
            DBSQLScript script = new DBSQLScript();
            Connection conn = driver.getConnection();
            getCreateDDLScript(driver.getDriver(), script);
            script.run(driver.getDriver(), conn, false);
            commit(conn);

            addDefaultData(conn);


        } catch (Exception e) {
          // table already created...
        }
        return this;
    }

    private void addDefaultData(Connection conn) {

        new DataSet()
                .withColumns(of(LogLevel.LogLevelId, LogLevel.Name))
                .withValues(of(
                        0, "ERROR",
                        1, "INFO",
                        2, "DEBUG"))
                .insertInto(LogLevel, conn);

        commit(conn);
    }
}