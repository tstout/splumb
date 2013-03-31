package splumb.common.db.schema;

import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;

class AddColumnCommand implements SchemaCommand {
    final String tableName;
    final DBTableColumn col;

    public AddColumnCommand(String tableName, DBTableColumn col) {
        this.tableName = tableName;
        this.col = col;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDatabase database) {
        DBTable tbl = database.getTable(tableName);
        tbl = tbl == null ? new TableDef(tableName, database) : tbl;

        DBSQLScript script = new DBSQLScript();
        driver.getDriver().getDDLScript(DBCmdType.CREATE, ((TableDef)tbl).addCol(col), script);
        return script;
    }
}
