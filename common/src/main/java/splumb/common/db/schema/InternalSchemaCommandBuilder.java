package splumb.common.db.schema;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;

import java.util.List;
import java.util.Map;

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
        checkNotNull(schemaName, "schema schemaName is required");

        List<DBSQLScript> commands = newArrayList();

        DBDatabase db = new DBDatabase(schemaName) { {
              open(InternalSchemaCommandBuilder.this.driver.getDriver(),
                      InternalSchemaCommandBuilder.this.driver.getConnection());
            }
        };

        for (Map.Entry<SchemaVersion, SchemaCommand> cmdEntry : buildCommands().entries()) {
            commands.add(cmdEntry.getValue().createDDL(driver, db, cmdEntry.getKey()));
        }
        return commands;
    }

    private Multimap<SchemaVersion, SchemaCommand> buildCommands() {
        Multimap<SchemaVersion, SchemaCommand> commands = ArrayListMultimap.create();

        SchemaMgmtDb smDB = new SchemaMgmtDb(driver);
        smDB.open(driver.getDriver(), driver.getConnection());

        for (SchemaModule module : modules) {
            module.configure(new Mutator(smDB, commands, driver));
        }
        return commands;
    }
}
