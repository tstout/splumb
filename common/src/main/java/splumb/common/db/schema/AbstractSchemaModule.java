package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

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

    protected void addColumns(String tableName, DBTableColumn... columns) {
        mutator.addColumns(version(), tableName, columns);
    }

    protected void dropColumns(DBTable table, DBTableColumn... columns) {
        mutator.dropColumns(version(), table, columns);
    }

    protected void addIndex(DBTable table, DBTableColumn... columns) {
        mutator.addIndex(version(), table, columns);
    }

    protected void addFK(DBTableColumn src, DBTableColumn dest) {
        mutator.addFK(version(), src, dest);
    }
}



