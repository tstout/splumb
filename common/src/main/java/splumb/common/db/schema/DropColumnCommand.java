package splumb.common.db.schema;

import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;

class DropColumnCommand implements SchemaCommand {
    final DBTable table;
    final DBTableColumn col;

    DropColumnCommand(DBTable table, DBTableColumn col) {
        this.table = table;
        this.col = col;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDatabase database, SchemaVersion version) {
        DBSQLScript dropScript = new DBSQLScript();
        driver.getDriver().getDDLScript(DBCmdType.DROP, col, dropScript);
        return dropScript;
    }
}
