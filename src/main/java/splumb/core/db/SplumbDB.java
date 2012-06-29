package splumb.core.db;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.empire.db.*;
import splumb.core.db.tables.Log;
import splumb.core.db.tables.LogLevel;

import java.sql.Connection;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.ImmutableSet.of;

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

        new DataSet()
                .withColumns(of(LogLevel.LogLevelId, LogLevel.Name))
                .withValues(of(
                        0, "ERROR",
                        1, "INFO",
                        2, "DEBUG"))
                .insertInto(LogLevel, conn);

        commit(conn);
    }

//    private void insertRow(ImmutableMap<DBColumn, Object> rowData, Connection conn) {
//        DBRecord record = new DBRecord();
//        record.create(LogLevel, conn);
//
//        for (Map.Entry<DBColumn, Object> row : rowData.entrySet()) {
//            record.setValue(row.getKey(), row.getValue());
//        }
//        record.update(conn);
//    }

    class DataSet {
        private ImmutableSet<DBTableColumn> columns;
        private ImmutableSet values;
        private DBDriver driver;

        public DataSet withDriver(DBDriver driver) {
            this.driver = driver;
            return this;
        }

        public DataSet withColumns(ImmutableSet<DBTableColumn> columns) {
            this.columns = columns;
            return this;
        }

        public DataSet withValues(ImmutableSet values) {
            this.values = values;
            return this;
        }

        public void insertInto(DBTable table, Connection conn) {
            checkState((values.size() % columns.size()) == 0, "Num columns does not match values");

            Object[] valueArray = values.toArray();
            ImmutableList<DBTableColumn> columnList = columns.asList();


            for (int i = 0; i <  values.size(); i += columns.size()) {

                DBRecord record = new DBRecord();
                record.create(table, conn);

                for (int j = 0; j < columnList.size(); j++) {
                    record.setValue(columnList.get(j), valueArray[i + j]);
                }

                record.update(conn);
            }
        }
    }


}
