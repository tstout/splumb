package splumb.common.db.schema;

import org.apache.empire.db.DBTable;

class AddTableCommand implements SchemaCommand {
    final DBTable table;

    AddTableCommand(DBTable table) {
        this.table = table;
    }
}
