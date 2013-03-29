package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DataSet;

import java.sql.Connection;

import static com.google.common.collect.ImmutableSet.*;
import static splumb.common.db.Columns.*;

/**
 * Contains all Database Object types that can be tracked using Splumb's schema versioning.
 */
public class SchemaObjectType extends DBTable implements HasDefaults {

    public final DBTableColumn TYPE = varchar("TYPE", 100);
    public final DBTableColumn DESCRIPTION = varchar("DESCRIPTION", 1000);

    public SchemaObjectType() {
        super(SchemaObject.class.getName(), null);
    }

    public SchemaObjectType(DBDatabase db) {
        super(SchemaObjectType.class.getName(), db);
        setPrimaryKey(TYPE);
    }

    @Override
    public void setDefaults(Connection connection) {
        new DataSet()
                .withColumns(of(TYPE, DESCRIPTION))
                .withValues(of(
                        "index", "vanilla index",
                        "foreign_key", "vanilla foreign key",
                        "column", "vanilla column",
                        "table", "vanilla table"))
                .insertInto(this, connection);
    }
}
