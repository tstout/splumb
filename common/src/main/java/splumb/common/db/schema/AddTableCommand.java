package splumb.common.db.schema;

import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTable;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.DBDef;

class AddTableCommand implements SchemaCommand {
    private final String tableName;

    AddTableCommand(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {
        DBSQLScript script = new DBSQLScript();

        driver.getDriver().getDDLScript(DBCmdType.CREATE,
                new DBTable(tableName, database.unwrap(DBDatabase.class)),
                script);

        return script;
    }
}
