package splumb.common.db.schema;

import com.google.common.base.Joiner;
import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBIndex;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;

import java.util.List;

import static com.google.common.collect.Lists.*;

class AddIndexCommand implements SchemaCommand {
    final DBTable table;
    final List<DBTableColumn> columns;

    public AddIndexCommand(DBTable table, List<DBTableColumn> columns) {
        this.table = table;
        this.columns = columns;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDatabase database) {
        DBSQLScript script = new DBSQLScript();

        //columns.get(0).set

        driver.getDriver()
                .getDDLScript(DBCmdType.CREATE,
                        new DBIndex(indexName(), DBIndex.PRIMARYKEY, toCol(columns)),
                        script);
        return script;
    }

    private String indexName() {
        List<String> colNames = newArrayList();

        for (DBTableColumn col : columns) {
            colNames.add(col.getName());
        }
        return String.format("%s_%s", table.getName(), Joiner.on('_').join(colNames));
    }

    private DBColumn[] toCol(List<DBTableColumn> cols) {
        // YUCK...must be a better way...
        List<DBColumn> columnList = newArrayList();
        for (DBTableColumn col : cols) {
            columnList.add(col);
        }

        return (DBColumn[])columnList.toArray();
    }
}
