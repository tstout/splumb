package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

class DropColumnCommand implements SchemaCommand {
    final DBTable table;
    final DBTableColumn col;

    public DropColumnCommand(DBTable table, DBTableColumn col) {
        this.table = table;
        this.col = col;
    }
}
