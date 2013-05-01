package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.DBDef;

public interface SchemaCommand {
    DBSQLScript createDDL(DBDriver driver, DBDef database, SchemaVersion version);

    //void runDDL(DBDriver driver, DBDef database, SchemaVersion version);
}
