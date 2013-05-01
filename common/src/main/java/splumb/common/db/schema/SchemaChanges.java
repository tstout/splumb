package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;


/**
 * Table representing a list of all schema versions
 */
public class SchemaChanges extends DBTable {
    public static final String TABLE_NAME = "SCHEMA_CHANGES";

    public final DBTableColumn VERSION = varchar(this, "VERSION", 25);
    public final DBTableColumn DATABASE_NAME = varchar(this, "DATABASE_NAME", 100);
    public final DBTableColumn DESCRIPTION = varchar(this, "DESCRIPTION", 1024);
    public final DBTableColumn DATE_TM = dateTime(this, "DATE_TM");

    public SchemaChanges() {
        super(TABLE_NAME, null);

    }

    public SchemaChanges(DBDatabase db) {
        super(TABLE_NAME, db);
        //setPrimaryKey(VERSION, DATABASE_NAME);
    }
}
