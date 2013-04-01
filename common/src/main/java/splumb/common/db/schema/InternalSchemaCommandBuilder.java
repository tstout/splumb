package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.*;

/**
 * Creates a list of DDL statements for the corresponding SchemaModules.
 */
class InternalSchemaCommandBuilder {
    private DBDriver driver;
    private SchemaModule[] modules;
    private String schemaName;

    InternalSchemaCommandBuilder driver(DBDriver driver) {
        this.driver = driver;
        return this;
    }

    InternalSchemaCommandBuilder schemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    InternalSchemaCommandBuilder modules(SchemaModule... modules) {
        this.modules = modules;
        return this;
    }

    List<DBSQLScript> build() {
        checkNotNull(schemaName, "schema name is required");

        List<DBSQLScript> commands = newArrayList();

        DBDatabase db = new DBDatabase(schemaName) { {
              open(InternalSchemaCommandBuilder.this.driver.getDriver(),
                      InternalSchemaCommandBuilder.this.driver.getConnection());
            }
        };

        for (SchemaCommand cmd : buildCommands()) {
            commands.add(cmd.createDDL(driver, db));
        }
        return commands;
    }

    private List<SchemaCommand> buildCommands() {
        List<SchemaCommand> commands = newArrayList();
        Mutator mutator = new Mutator(commands, driver);

        for (SchemaModule module : modules) {
            module.configure(mutator);
        }
        return commands;
    }
}
