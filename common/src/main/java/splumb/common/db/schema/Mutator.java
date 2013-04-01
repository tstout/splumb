package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.DBDriver;

import java.util.Arrays;
import java.util.List;

class Mutator implements SchemaMutator {
    List<SchemaCommand> commands;
    DBDriver driver;
    SchemaMgmtDb smDB;

    Mutator(List<SchemaCommand> commands, DBDriver driver) {
        this.commands = commands;
        this.driver = driver;
        smDB = new SchemaMgmtDb(driver);
    }

    @Override
    public SchemaMutator addTables(SchemaVersion version, String... tableNames) {
        for (String tableName : tableNames) {
            commands.add(new AddTableCommand(tableName));
        }
        return this;
    }

    @Override
    public SchemaMutator addColumns(SchemaVersion version, String tableName, DBTableColumn... columns) {
        for (DBTableColumn col : columns) {
            commands.add(new AddColumnCommand(tableName, col));
        }
        return this;
    }

    @Override
    public SchemaMutator dropColumns(SchemaVersion version, DBTable table, DBTableColumn... columns) {
        for (DBTableColumn col : columns) {
            commands.add(new DropColumnCommand(table, col));
        }
        return this;
    }

    @Override
    public SchemaMutator addIndex(SchemaVersion version, DBTable table, DBTableColumn... columns) {
        commands.add(new AddIndexCommand(table, Arrays.asList(columns)));
        return this;
    }

    @Override
    public SchemaMutator addFK(SchemaVersion version, DBTableColumn src, DBTableColumn dest) {
        commands.add(new AddFKCommand(src, dest));
        return this;
    }
}

