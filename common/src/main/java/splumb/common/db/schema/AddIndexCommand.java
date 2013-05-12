package splumb.common.db.schema;

import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBIndex;
import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.ColumnDef;
import splumb.common.db.schema.definition.DBDef;

import java.util.List;

class AddIndexCommand implements SchemaCommand {
    final TableDef2 table;
    final List<ColumnDef> columns;

    public AddIndexCommand(TableDef2 table, List<ColumnDef> columns) {
        this.table = table;
        this.columns = columns;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {
        DBSQLScript script = new DBSQLScript();

        //columns.get(0).set

        driver.getDriver()
                .getDDLScript(DBCmdType.CREATE,
                        new DBIndex(indexName(), DBIndex.PRIMARYKEY, toCol(columns)),
                        script);
        return script;
    }


    private String indexName() {
//        List<String> colNames = newArrayList();
//
//        for (DBTableColumn col : columns) {
//            colNames.add(col.getName());
//        }
//        return String.format("%s_%s", table.getName(), Joiner.on('_').join(colNames));
        return null;
    }

    private DBColumn[] toCol(List<ColumnDef> cols) {
//        // YUCK...must be a better way...
//        List<DBColumn> columnList = newArrayList();
//        for (ColumnDef col : cols) {
//            columnList.add(col);
//        }
//
//        return (DBColumn[])columnList.toArray();
        return new DBColumn[] {};
    }
}
