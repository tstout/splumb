package splumb.common.db.schema.definition;

import org.apache.empire.db.DBColumn;
import org.junit.Test;
import splumb.common.db.H2InMemDriver;
import splumb.common.db.schema.ColType;
import splumb.common.db.schema.TableDefBuilder;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;
import static splumb.common.db.Columns.*;

public class ColTypeTest {
    TableDef tableDef = new TableDefBuilder()
            .withName("Test")
            .withDb(new DBDefImpl("TypeTest", new H2InMemDriver()))
            .build();

    @Test
    public void intConvert() {
        DBColumn col = ColType.INT.convert(tableDef, intCol("SAMPLE_INT"), DBColumn.class);

        assertThat(col.getDataType().isNumeric(), is(true));
        assertThat(col.getName(), is("SAMPLE_INT"));
    }

    @Test
    public void charConvert() {
        DBColumn col = ColType.VARCHAR.convert(tableDef, varchar("CHAR_COL", 64), DBColumn.class);
        assertThat(col.getDataType().isText(), is(true));
        assertThat(col.getName(), is("CHAR_COL"));
    }

    @Test
    public void dateTimeConvert() {
        DBColumn  col = ColType.DATE_TIME.convert(tableDef, dateTime("DT_COL"), DBColumn.class);
        assertThat(col.getDataType().isDate(), is(true));
        assertThat(col.getName(), is("DT_COL"));
    }

    @Test
    public void autoIncConvert() {
        DBColumn  col = ColType.AUTOINC.convert(tableDef, dateTime("AI_COL"), DBColumn.class);
        assertThat(col.getDataType().isNumeric(), is(true));
        assertThat(col.getName(), is("AI_COL"));
    }
}
