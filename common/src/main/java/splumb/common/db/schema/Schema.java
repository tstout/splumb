package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.WithConnection;
import splumb.common.func.Action;

import java.sql.Connection;

public class Schema {
    private final DBDriver driver;

    public Schema(DBDriver driver) {
        this.driver = driver;
    }

    public void createMgmtTables() {
        if (!tableExists("SCHEMA_CHANGES", "SPLUMB")) {
            create("SPLUMB", new BootstrapSchemaModule());
        }
    }

    public boolean tableExists(String tableName, String schema) {
        return new TableListBuilder()
                .driver(driver)
                .schemaPattern(schema)
                .tablePattern(tableName)
                .build()
                .size() != 0;
    }

    public void create(final String schemaName, final SchemaModule... modules) {

        new WithConnection().exec(driver, new Action<Connection>() {
            @Override public void invoke(Connection input) {
                for (DBSQLScript script : new InternalSchemaCommandBuilder()
                        .driver(driver)
                        .schemaName(schemaName)
                        .modules(modules)
                        .build()) {
                    script.run(driver.getDriver(), input, false);
                }
            }
        });
    }
//
//    interface SchemaFilter {
//        boolean shouldCreate();
//    }
//
//    class CheckIfExists implements SchemaFilter {
//
//        @Override
//        public boolean shouldCreate() {
//            DBDriver driver = Schema.this.driver;
//            ResultSetdriver.getConnection().getMetaData().getTable()
//        }
//    }



}
