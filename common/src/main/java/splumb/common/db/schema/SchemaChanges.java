package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;


/**
 * Table representing a list of all schema versions
 */
public class SchemaChanges extends DBTable {

    public final DBTableColumn VERSION = varchar("VERSION", 25);
    public final DBTableColumn DATABASE_NAME = varchar("DATABASE_NAME", 100);
    public final DBTableColumn DESCRIPTION = varchar("DESCRIPTION", 1024);
    public final DBTableColumn DATE_TM = dateTime(this, "DATE_TM");

    public SchemaChanges() {
        super(SchemaChanges.class.getName(), null);

    }

    public SchemaChanges(DBDatabase db) {
        super(SchemaChanges.class.getName(), db);
        setPrimaryKey(VERSION, DATABASE_NAME);
    }
}
