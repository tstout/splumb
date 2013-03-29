package splumb.common.db.schema;

import org.junit.Test;
import splumb.common.db.H2InMemDriver;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class SchemaMgmtTest {

    @Test
    public void moduleLoadTest() {
        List<SchemaCommand> commands = new InternalSchemaCommandBuilder()
                .driver(new H2InMemDriver())
                .modules(new BootstrapSchemaModule())
                .build();

        assertThat(commands.size(), not(0));
    }
}
