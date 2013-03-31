package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import org.junit.Test;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SchemaMgmtTest {
    H2InMemDriver memDB;

    @Test
    public void moduleCreateDDLTest() {

        List<DBSQLScript> commands = new InternalSchemaCommandBuilder()
                .driver(new H2InMemDriver())
                .schemaName("splumb")
                .modules(new BootstrapSchemaModule())
                .build();

        assertThat(commands.size(), not(0));
    }

    @Test
    public void moduleRunDDLTest() {
        DBDriver driver = new H2InMemDriver();

        Schemas.create(driver, "splumb", new BootstrapSchemaModule());
        //assertThat(Schemas.tableExists(driver, "SCHEMA_VERSION", "splumb"), is(true));
        assertThat(Schemas.tableExists(driver, "SCHEMA_CHANGES", "SPLUMB"), is(true));
    }

}
