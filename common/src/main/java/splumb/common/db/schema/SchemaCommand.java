package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;

public interface SchemaCommand {
    DBSQLScript createDDL(DBDriver driver, DBDatabase database, SchemaVersion version);

    void runDDL(DBDriver driver, DBDatabase database, SchemaVersion version);
}
