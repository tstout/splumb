package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;

public class SchemaObject extends DBTable {
    public final static String TABLE_NAME = "SCHEMA_OBJECT";

    public final DBTableColumn SCHEMA_OBJECT_ID = autoInc("SCHEMA_OBJECT_ID");
    public final DBTableColumn SCHEMA_OBJECT_TYPE = varchar("SCHEMA_OBJECT_TYPE", 100);
    public final DBTableColumn SCHEMA_VERSION = varchar("SCHEMA_VERSION", 100);
    public final DBTableColumn CREATION_DATE = dateTime("CREATION_DATE");
    public final DBTableColumn REMOVED_DATE = dateTime("REMOVED_DATE");

    public SchemaObject() {
        super(TABLE_NAME, null);
    }

    public SchemaObject(DBDatabase db) {
        super(TABLE_NAME, db);
        //setPrimaryKey(SCHEMA_OBJECT_ID);
    }
}
