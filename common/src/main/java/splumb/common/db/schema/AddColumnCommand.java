package splumb.common.db.schema;

import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.ColumnDef;
import splumb.common.db.schema.definition.DBDef;
import splumb.common.db.schema.definition.TableDef;

class AddColumnCommand implements SchemaCommand {
    private final String tableName;
    private final ColumnDef col;

    AddColumnCommand(String tableName, ColumnDef col) {
        this.tableName = tableName;
        this.col = col;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {

        TableDef tblDef = new TableDefBuilder()
                .withName(tableName)
                .withDb(database)
                .build();

        DBSQLScript ddlScript = new DBSQLScript();

        driver.getDriver()
                .getDDLScript(DBCmdType.CREATE, col.type().convert(tblDef, col, DBTableColumn.class), ddlScript);

        return ddlScript;
    }
}
