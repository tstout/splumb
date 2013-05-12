package splumb.common.db.schema.definition;

import org.apache.empire.db.DBColumn;
import org.junit.Test;
import splumb.common.db.Columns;
import splumb.common.db.H2InMemDriver;
import splumb.common.db.schema.ColType;
import splumb.common.db.schema.TableDefBuilder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ColTypeTest {
    TableDef tableDef = new TableDefBuilder()
            .withName("Test")
            .withDb(new DBDefImpl("TypeTest", new H2InMemDriver()))
            .build();

    @Test
    public void intConvert() {
        ColumnDef colDef = Columns.col()
                .ofInt()
                .withName("SAMPLE_INT")
                .build();

        DBColumn col = ColType.INT.convert(tableDef, colDef, DBColumn.class);

        assertThat(col.getDataType().isNumeric(), is(true));
        assertThat(col.getName(), is("SAMPLE_INT"));
    }
}
