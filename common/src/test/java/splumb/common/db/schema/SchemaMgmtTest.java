package splumb.common.db.schema;

import org.apache.empire.db.DBSQLScript;
import org.junit.Test;
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
        Schemas.create(new H2InMemDriver(), "splumb", new BootstrapSchemaModule());
    }

}
