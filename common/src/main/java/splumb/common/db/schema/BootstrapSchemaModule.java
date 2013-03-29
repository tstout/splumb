package splumb.common.db.schema;

import static splumb.common.db.Columns.*;

public class BootstrapSchemaModule extends AbstractSchemaModule {
    @Override
    public SchemaVersion version() {
        return SchemaVersion.builder()
                .withMajor(1)
                .withMinor(0)
                .withPoint(0)
                .build();
    }

    @Override
    protected void configure() {
        addTables(new SchemaObject(),
                new SchemaChanges(),
                new SchemaObjectType());

        addColumns(new SchemaObject(),
                autoInc("SCHEMA_OBJECT_ID"),
                varchar("SCHEMA_OBJECT_TYPE", 100),
                varchar("SCHEMA_VERSION", 100),
                dateTime("CREATION_DATE"),
                dateTime("REMOVED_DATE"));

        addColumns(new SchemaChanges(),
                varchar("VERSION", 25),
                varchar("DATABASE_NAME", 100),
                varchar("DESCRIPTION", 1024),
                dateTime("DATE_TM"));

        addColumns(new SchemaObjectType(),
                varchar("TYPE", 100),
                varchar("DESCRIPTION", 1000));
    }
}
