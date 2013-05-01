package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.DBDef;

class AddTableCommand implements SchemaCommand {
    private final String tableName;

    AddTableCommand(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {
        //TableDef tbl = new TableDef(tableName, database);
        DBSQLScript script = new DBSQLScript();
        //driver.getDriver().getDDLScript(DBCmdType.CREATE, tbl, script);
        return script;
    }
}
