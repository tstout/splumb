package splumb.common.db.schema;

import static splumb.common.db.Columns.*;

public class BootstrapSchemaModule extends AbstractSchemaModule {
    @Override
    public SchemaVersion version() {
        return SchemaVersion.builder()
                .withMajor(1)
                .withMinor(0)
                .withPoint(0)
                .withSchemaName("SPLUMB")
                .build();
    }

    @Override
    protected void configure() {
        addTables(SchemaObject.TABLE_NAME,
                SchemaChanges.TABLE_NAME,
                SchemaObjectType.TABLE_NAME);

        addColumns(SchemaObject.TABLE_NAME,
                autoInc("OBJECT_ID"),
                varchar("OBJECT_TYPE", 100),
                varchar("OBJECT_NAME", 100),
                varchar("PARENT_OBJECT_NAME", 100),
                intCol("VERSION_MAJOR"),
                intCol("VERSION_MINOR"),
                intCol("VERSION_POINT"),
                dateTime("CREATION_DATE"),
                dateTime("REMOVED_DATE"));

        addColumns(SchemaChanges.TABLE_NAME,
                varchar("VERSION", 25),
                varchar("DATABASE_NAME", 100),
                varchar("DESCRIPTION", 1024),
                dateTime("DATE_TM"));

        addColumns(SchemaObjectType.TABLE_NAME,
                varchar("TYPE", 100),
                varchar("DESCRIPTION", 1000));
    }
}
