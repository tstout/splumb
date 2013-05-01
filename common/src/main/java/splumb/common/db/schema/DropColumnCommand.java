package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.ColumnDef;
import splumb.common.db.schema.definition.DBDef;

class DropColumnCommand implements SchemaCommand {
    final TableDef table;
    final ColumnDef col;

    DropColumnCommand(TableDef table, ColumnDef col) {
        this.table = table;
        this.col = col;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {
        DBSQLScript dropScript = new DBSQLScript();

        // TODO - this is busted...
        //DBColumn dbCol = Columns.col().withName(col.name()).build();

        //driver.getDriver().getDDLScript(DBCmdType.DROP, col, dropScript);
        return dropScript;
    }
}
