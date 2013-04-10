package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;

public class SchemaObject extends DBTable {
    public final static String TABLE_NAME = "SCHEMA_OBJECT";

    public final DBTableColumn OBJECT_ID = autoInc(this, "OBJECT_ID");
    public final DBTableColumn OBJECT_TYPE = varchar(this, "OBJECT_TYPE", 100);
    public final DBTableColumn OBJECT_NAME = varchar(this, "OBJECT_NAME", 100);
    public final DBTableColumn PARENT_OBJECT_NAME = varchar(this, "PARENT_OBJECT_NAME", 100);
    public final DBTableColumn VERSION_MAJOR = intCol(this, "VERSION_MAJOR");
    public final DBTableColumn VERSION_MINOR = intCol(this, "VERSION_MINOR");
    public final DBTableColumn VERSION_POINT = intCol(this, "VERSION_POINT");
    public final DBTableColumn SCHEMA_NAME = varchar(this, "SCHEMA_NAME", 100);
    public final DBTableColumn CREATION_DATE = dateTime(this, "CREATION_DATE");
    public final DBTableColumn REMOVED_DATE = dateTime(this, "REMOVED_DATE");

    public SchemaObject() {
        super(TABLE_NAME, null);
    }

    public SchemaObject(DBDatabase db) {
        super(TABLE_NAME, db);
        //setPrimaryKey(SCHEMA_OBJECT_ID);
    }
}
