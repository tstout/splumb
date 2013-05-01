package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.DBDef;

class AddFKCommand implements SchemaCommand {
    final DBTableColumn src;
    final DBTableColumn dest;

    public AddFKCommand(DBTableColumn src, DBTableColumn dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override public DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version) {
        throw new UnsupportedOperationException();
    }
}
