package splumb.core.db;

import com.google.common.collect.ImmutableMap;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBRecord;
import org.apache.empire.db.DBSQLScript;
import splumb.core.db.tables.Log;
import splumb.core.db.tables.LogLevel;

import java.sql.Connection;
import java.util.Map;

public class SplumbDB extends DBDatabase {

    public final Log Log = new Log(this);
    public final LogLevel LogLevel = new LogLevel(this);

    private DBDriver driver;

    public SplumbDB() {
        addRelation(Log.Level.referenceOn(LogLevel.LogLevelId));
    }

    public SplumbDB create(DBDriver driver) {
        this.open(driver.getDriver(), driver.getConnection());
        this.driver = driver;

        try {
            //
            // TODO...empire DB must provide a better way...
            //
            DBSQLScript script = new DBSQLScript();
            Connection conn = driver.getConnection();
            getCreateDDLScript(driver.getDriver(), script);
            script.run(driver.getDriver(), conn, false);
            commit(conn);

            addDefaultData();


        } catch (Exception e) {
          // table already created...
        }
        return this;
    }

    private void addDefaultData() {

        Connection conn = driver.getConnection();

        // TODO - remove this try/catch ...needed only for debug...
        try {

        insertRow(ImmutableMap.<DBColumn, Object>of(
                LogLevel.LogLevelId, 0,
                LogLevel.Name, "ERROR"),
                conn);

        insertRow(ImmutableMap.<DBColumn, Object>of(
                LogLevel.LogLevelId, 1,
                LogLevel.Name, "INFO"),
                conn);

        insertRow(ImmutableMap.<DBColumn, Object>of(
                LogLevel.LogLevelId, 2,
                LogLevel.Name, "DEBUG"),
                conn);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        commit(conn);
    }

    private void insertRow(ImmutableMap<DBColumn, Object> rowData, Connection conn) {
        DBRecord record = new DBRecord();
        record.create(LogLevel, conn);

        for (Map.Entry<DBColumn, Object> row : rowData.entrySet()) {
            record.setValue(row.getKey(), row.getValue());
        }
        record.update(conn);
    }

}
