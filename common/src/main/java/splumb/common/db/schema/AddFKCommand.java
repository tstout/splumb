package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;

class AddFKCommand implements SchemaCommand {
    final DBTableColumn src;
    final DBTableColumn dest;

    public AddFKCommand(DBTableColumn src, DBTableColumn dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public DBSQLScript createDDL(DBDriver driver, DBDatabase database, SchemaVersion version) {
        throw new UnsupportedOperationException();
    }

    @Override public void runDDL(DBDriver driver, DBDatabase database, SchemaVersion version) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
