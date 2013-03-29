package splumb.common.db.schema;

import org.apache.empire.db.DBTableColumn;

class AddFKCommand implements SchemaCommand {
    final DBTableColumn src;
    final DBTableColumn dest;

    public AddFKCommand(DBTableColumn src, DBTableColumn dest) {
        this.src = src;
        this.dest = dest;
    }
}
