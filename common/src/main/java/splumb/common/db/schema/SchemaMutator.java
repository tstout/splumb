package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

interface SchemaMutator {
    SchemaMutator addTables(SchemaVersion version, String... tableNames);

    SchemaMutator addColumns(SchemaVersion version, String tableName, DBTableColumn... columns);

    SchemaMutator dropColumns(SchemaVersion version, DBTable table, DBTableColumn... columns);

    SchemaMutator addIndex(SchemaVersion version, DBTable table, DBTableColumn... columns);

    SchemaMutator addFK(SchemaVersion version, DBTableColumn src, DBTableColumn dest);
}
