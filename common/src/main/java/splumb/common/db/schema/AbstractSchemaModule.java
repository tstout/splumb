package splumb.common.db.schema;

import splumb.common.db.schema.definition.ColumnDef;

public abstract class AbstractSchemaModule implements SchemaModule {
    private SchemaMutator mutator; // NO-op mutator that throws?

    @Override
    public abstract SchemaVersion version();

    @Override
    public final void configure(SchemaMutator mutator) {
        this.mutator = mutator;
        configure();
    }

    protected abstract void configure();

    protected void addTables(String... tableNames) {
        mutator.addTables(version(), tableNames);
    }

    protected void addColumns(String tableName, ColumnDef... columns) {
        mutator.addColumns(version(), tableName, columns);
    }

    protected void dropColumns(TableDef table, ColumnDef... columns) {
        mutator.dropColumns(version(), table, columns);
    }

    protected void addIndex(TableDef table, ColumnDef... columns) {
        mutator.addIndex(version(), table, columns);
    }

    protected void addFK(ColumnDef src, ColumnDef dest) {
        mutator.addFK(version(), src, dest);
    }
}



