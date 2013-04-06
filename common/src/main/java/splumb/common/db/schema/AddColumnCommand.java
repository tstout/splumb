package splumb.common.db.schema;

import com.google.common.base.Predicate;
import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;

import static com.google.common.collect.FluentIterable.*;

class AddColumnCommand implements SchemaCommand {
    private final String tableName;
    private final DBTableColumn col;
    private final SchemaMgmtDb smDb;
    private DBSQLScript ddlScript;

    public AddColumnCommand(String tableName, DBTableColumn col, SchemaMgmtDb smDb) {
        this.tableName = tableName;
        this.col = col;
        this.smDb = smDb;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDatabase database, SchemaVersion version) {
        DBTable tbl = database.getTable(tableName);
        tbl = tbl == null ? new TableDef(tableName, database) : tbl;

        ddlScript = new DBSQLScript();

        if (!from(new SchemaHistory(driver, smDb).forTable(tableName, version))
                .firstMatch(Fn.colExists(col))
                .isPresent()) {
            driver.getDriver()
                    .getDDLScript(DBCmdType.CREATE, ((TableDef) tbl)
                            .addCol(col), ddlScript);
        }

        return ddlScript;
    }

    @Override public void runDDL(DBDriver driver, DBDatabase database, SchemaVersion version) {
//        if (ddlScript.getCount() > 0) {
//            ddlScript.run(driver.getDriver(), driver.getConnection(), false);
//            new DataSet()
//
//
//        }

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
