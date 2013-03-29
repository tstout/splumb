package splumb.common.db.schema;

import splumb.common.db.DBDriver;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

class InternalSchemaCommandBuilder {
    private DBDriver driver;
    private SchemaModule[] modules;

    InternalSchemaCommandBuilder driver(DBDriver driver) {
        this.driver = driver;
        return this;
    }

    InternalSchemaCommandBuilder modules(SchemaModule... modules) {
        this.modules = modules;
        return this;
    }

    List<SchemaCommand> build() {
        List<SchemaCommand> commands = newArrayList();
        Mutator mutator = new Mutator(commands);

        for (SchemaModule module : modules) {
            module.configure(mutator);
        }
        return commands;
    }
}
