package splumb.common.db.schema;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.*;

/**
 * Creates a list of DDL statements for the corresponding SchemaModules.
 */
class InternalSchemaCommandBuilder {
    private DBDriver driver;
    private SchemaModule[] modules;
    private final DBDefImpl db;

    InternalSchemaCommandBuilder(DBDefImpl db) {
        this.db = db;
    }

    InternalSchemaCommandBuilder driver(DBDriver driver) {
        this.driver = driver;
        return this;
    }

//    InternalSchemaCommandBuilder dbSupplier(Supplier<DBDatabase> dbSupplier) {
//        this.dbSupplier = dbSupplier;
//        return this;
//    }

    InternalSchemaCommandBuilder modules(SchemaModule... modules) {
        this.modules = modules;
        return this;
    }

    List<DBSQLScript> build() {
        List<DBSQLScript> commands = newArrayList();

        for (Map.Entry<SchemaVersion, SchemaCommand> cmdEntry : buildCommands().entries()) {
            commands.add(cmdEntry.getValue().createDDL(driver, db, cmdEntry.getKey()));
        }

        //return buildCommands();
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
