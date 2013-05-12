package splumb.common.db.schema;

import splumb.common.db.schema.definition.ColumnDef;

interface SchemaMutator {
    SchemaMutator addTables(SchemaVersion version, String... tableNames);

    SchemaMutator addColumns(SchemaVersion version, String tableName, ColumnDef... columns);

    SchemaMutator dropColumns(SchemaVersion version, TableDef2 table, ColumnDef... columns);

    SchemaMutator addIndex(SchemaVersion version, TableDef2 table, ColumnDef... columns);

    SchemaMutator addFK(SchemaVersion version, ColumnDef src, ColumnDef dest);
}
