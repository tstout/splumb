package splumb.common.db.schema;

import com.google.common.collect.Multimap;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.ColumnDef;

import java.util.Arrays;

class Mutator implements SchemaMutator {
    Multimap<SchemaVersion, SchemaCommand> commands;
    DBDriver driver;
    SchemaMgmtDb smDB;

    Mutator(SchemaMgmtDb smDB, Multimap<SchemaVersion, SchemaCommand> commands, DBDriver driver) {
        this.commands = commands;
        this.driver = driver;
        this.smDB = smDB;
    }

    @Override
    public SchemaMutator addTables(SchemaVersion version, String... tableNames) {
        for (String tableName : tableNames) {
            commands.put(version, new AddTableCommand(tableName));
        }
        return this;
    }

    @Override
    public SchemaMutator addColumns(SchemaVersion version, String tableName, ColumnDef... columns) {
        for (ColumnDef col : columns) {
            commands.put(version, new AddColumnCommand(tableName, col, smDB));
        }
        return this;
    }

    @Override
    public SchemaMutator dropColumns(SchemaVersion version, TableDef table, ColumnDef... columns) {
        for (ColumnDef col : columns) {
            commands.put(version, new DropColumnCommand(table, col));
        }
        return this;
    }

    @Override
    public SchemaMutator addIndex(SchemaVersion version, TableDef table, ColumnDef... columns) {
        commands.put(version, new AddIndexCommand(table, Arrays.asList(columns)));
        return this;
    }

    @Override
    public SchemaMutator addFK(SchemaVersion version, ColumnDef src, ColumnDef dest) {
        //commands.put(version, new AddFKCommand(src, dest));
        return this;
    }
}

