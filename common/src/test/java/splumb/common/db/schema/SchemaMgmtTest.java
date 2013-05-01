package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SchemaMgmtTest {

    private DBDriver driver;

    //@Before
    public void setup() {
        driver = new H2InMemDriver();
    }

    //@After
    public void tearDown() {
        driver.shutdown();
    }

    //@Test
    public void moduleCreateDDLTest() {

        //DBDriver driver = new H2InMemDriver();
        new Schema(driver).createMgmtTables();

        List<DBSQLScript> commands = new InternalSchemaCommandBuilder(null)
                .driver(new H2InMemDriver())
                //.schema("SPLUMB")
                .modules(new BootstrapSchemaModule())
                .build();

        assertThat(commands.size(), not(0));
    }

    //@Test
    public void moduleRunDDLTest() {
        //DBDriver driver = new H2InMemDriver();
        Schema schema = new Schema(driver);
        //schema.createMgmtTables();

        //schema.create("SPLUMB", new BootstrapSchemaModule());

        assertThat(schema.tableExists("SCHEMA_CHANGES", "SPLUMB"), is(true));
        assertThat(schema.tableExists("SCHEMA_OBJECT", "SPLUMB"), is(true));
        assertThat(schema.tableExists("SCHEMA_OBJECT_TYPE", "SPLUMB"), is(true));
    }
}
