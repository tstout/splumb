package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import splumb.common.db.DBDriver;

import javax.inject.Inject;

public class SchemaMgmtDB extends DBDatabase {
    public final DBTable SCHEMA_OBJECT = new SchemaObject(this);
    public final DBTable SCHEMA_OBJECT_TYPE = new SchemaObjectType(this);
    public final DBTable SCHEMA_VERSION = new SchemaChanges(this);

    private DBDriver driver;

    @Inject
    public SchemaMgmtDB(DBDriver driver) {
        super("SPLUMB");    // SCHEMA name
        this.driver = driver;
    }

    public SchemaMgmtDB() {
    }
}
