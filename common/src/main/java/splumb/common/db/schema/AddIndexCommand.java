package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import java.util.List;

class AddIndexCommand implements SchemaCommand {
    final DBTable table;
    final List<DBTableColumn> columns;

    public AddIndexCommand(DBTable table, List<DBTableColumn> columns) {
        this.table = table;
        this.columns = columns;
    }
}
