package splumb.common.db.schema;

import org.junit.Test;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.common.db.Columns.*;

public class SchemaDefTest {

    class DbDef extends AbstractSchemaModule {

        @Override public SchemaVersion version() {
            return SchemaVersion.builder()
                    .withMajor(1)
                    .withMinor(0)
                    .withPoint(0)
                    .withSchemaName("splumb")
                    .build();
        }

        @Override protected void configure() {
            addTables("BROKER");

            addColumns("BROKER",
                    varchar("HOST", 60),
                    dateTime("LAST_COMM"));
        }
    }

    @Test
    public void creatTable() {
        DBDriver driver = new H2InMemDriver();


        Schema schema = new Schema(driver).create(new DbDef());

        assertThat(schema.tableExists("BROKER", new DbDef().version().schemaName()), is(true));
    }
}
