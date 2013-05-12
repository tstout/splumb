package splumb.common.db.schema;

import com.google.common.base.Predicate;
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
    private final SchemaMgmtDb smDb;
    private DBSQLScript ddlScript;

    AddColumnCommand(String tableName, ColumnDef col, SchemaMgmtDb smDb) {
        this.tableName = tableName;
        this.col = col;
        this.smDb = smDb;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {

        TableDef tblDef = new TableDefBuilder()
                .withName(tableName)
                .withDb(database)
                .build();

        //DBTable tbl = database.unwrap(DBDatabase.class).getTable(tableName);
        ddlScript = new DBSQLScript();

        driver.getDriver()
                .getDDLScript(DBCmdType.CREATE, col.type().convert(tblDef, col, DBTableColumn.class), ddlScript);

        //Columns.col().


        //driver.getDriver().getDDLScript(DBCmdType.CREATE, tbl.addColumn(col), ddlScript);
//
//        tbl = tbl == null ? new TableDef2(tableName, database) : tbl;
//
//        ddlScript = new DBSQLScript();
//
//        if (!from(new SchemaHistory(driver, smDb)
//                .forTable(tableName, version))
//                .firstMatch(Fn.colExists(col))
//                .isPresent()) {
//            driver.getDriver()
//                    .getDDLScript(DBCmdType.CREATE, ((TableDef2) tbl)
//                            .addCol(col), ddlScript);
//        }

        return ddlScript;
    }

    static class Fn {
        static Predicate<SchemaHistoryRow> colExists(final DBTableColumn col) {
            return new Predicate<SchemaHistoryRow>() {
                @Override
                public boolean apply(SchemaHistoryRow input) {
                    return col.getName().equals(input.objectName()) &&
                            input.objectType().equals("COLUMN");
                }
            };
        }
    }
}
