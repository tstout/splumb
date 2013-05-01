package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.WithConnection;
import splumb.common.func.Action;

import java.sql.Connection;

import static splumb.common.db.schema.DBDefImpl.*;


public class Schema {
    private static final String SCHEMA_NAME = "splumb";
    private final DBDriver driver;

    public Schema(DBDriver driver) {
        this.driver = driver;
    }

    public void createMgmtTables() {
        if (!tableExists("SCHEMA_CHANGES", "SPLUMB")) {
            create(new BootstrapSchemaModule());
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

    public void create(final SchemaModule... modules) {

        // TODO - database needs to be closed...

//        final DBDatabase db = new DBDatabase(SCHEMA_NAME) {{
//            open(Schema.this.driver.getDriver(), Schema.this.driver.getConnection());
//        }};

        final DBDefImpl dbImpl = new DBDefImpl(EMPTY_TABLE_LIST, EMPTY_FK_LIST, SCHEMA_NAME, driver);


        new WithConnection().exec(driver, new Action<Connection>() {
            @Override public void invoke(Connection input) {
                for (DBSQLScript script : new InternalSchemaCommandBuilder(dbImpl)
                        .driver(driver)
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
