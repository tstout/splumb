package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import java.util.Arrays;
import java.util.List;

class Mutator implements SchemaMutator {
    List<SchemaCommand> commands;

    Mutator(List<SchemaCommand> commands) {
        this.commands = commands;
    }

    @Override
    public SchemaMutator addTables(String... tableNames) {
        for (String tableName : tableNames) {
            commands.add(new AddTableCommand(tableName));
        }
        return this;
    }

    @Override
    public SchemaMutator addColumns(String tableName, DBTableColumn... columns) {
        for (DBTableColumn col : columns) {
            commands.add(new AddColumnCommand(tableName, col));
        }
        return this;
    }

    @Override
    public SchemaMutator dropColumns(DBTable table, DBTableColumn... columns) {
        for (DBTableColumn col : columns) {
            commands.add(new DropColumnCommand(table, col));
        }
        return this;
    }

    @Override
    public SchemaMutator addIndex(DBTable table, DBTableColumn... columns) {
        commands.add(new AddIndexCommand(table, Arrays.asList(columns)));
        return this;
    }

    @Override
    public SchemaMutator addFK(DBTableColumn src, DBTableColumn dest) {
        commands.add(new AddFKCommand(src, dest));
        return this;
    }
}
