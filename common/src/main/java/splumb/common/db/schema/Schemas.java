package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;

import java.sql.Connection;
import java.sql.SQLException;

import static com.google.common.base.Throwables.*;

public class Schemas {

    public static boolean tableExists(DBDriver driver, String tableName, String schema) {
        return new TableListBuilder()
                .driver(driver)
                .schemaPattern(schema)
                .tablePattern(tableName)
                .build()
                .size() != 0;
    }

    public static void create(DBDriver driver, String schemaName, SchemaModule... modules) {
        Connection conn = null;
        try {
            conn = driver.getConnection();

            for (DBSQLScript script : new InternalSchemaCommandBuilder()
                    .driver(driver)
                    .schemaName(schemaName)
                    .modules(modules)
                    .build()) {
                script.run(driver.getDriver(), conn, false);
                conn.commit();
            }
        } catch (Exception e) {
            // YUCK...investigate a better way here...
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw propagate(e1);
            }
        }
    }
}
