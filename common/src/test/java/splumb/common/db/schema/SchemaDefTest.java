package splumb.common.db.schema;

import org.junit.Test;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;

import static splumb.common.db.Columns.*;

public class SchemaDefTest {

    class Def extends AbstractSchemaModule {

        @Override public SchemaVersion version() {
            return SchemaVersion.builder()
                    .withMajor(1)
                    .withMinor(0)
                    .withPoint(0)
                    .withSchemaName("TEST")
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


        new Schema(driver).create(new Def());
    }
}
