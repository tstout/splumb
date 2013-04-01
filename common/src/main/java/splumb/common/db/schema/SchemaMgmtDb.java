package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import splumb.common.db.DBDriver;

import javax.inject.Inject;

public class SchemaMgmtDb extends DBDatabase {
    public final SchemaObject SCHEMA_OBJECT = new SchemaObject(this);
    public final SchemaObjectType SCHEMA_OBJECT_TYPE = new SchemaObjectType(this);
    public final SchemaChanges SCHEMA_VERSION = new SchemaChanges(this);

    private DBDriver driver;

    @Inject
    public SchemaMgmtDb(DBDriver driver) {
        super("SPLUMB");    // SCHEMA name
        this.driver = driver;
    }

    public SchemaMgmtDb() {
    }
}
