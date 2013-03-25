package splumb.common.db;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import org.junit.Before;
import org.junit.Test;

import static splumb.common.db.Columns.*;

public class TableDefTest {

    class Log extends DBTable {

        public final DBTableColumn LOG_ID = autoInc(this, "LOG_ID");
        public final DBTableColumn LEVEL = intCol(this, "LEVEL");
        public final DBTableColumn DATE_TIME = dateTime(this, "DATE_TIME");
        public final DBTableColumn MSG = varchar(this, "MSG", 8096);
        public final DBTableColumn LOGGER = varchar(this, "LOGGER", 256);

        //public final DBTableColumn THREAD = varchar(this, "THREAD", 128);

        public Log(DBDatabase db) {
            super("LOG", db);
            setPrimaryKey(LOG_ID);
        }
    }

    class SampleDB extends DBDatabase {
        public final Log Log = new Log(this);
    }

    @Before
    public void setup() {

    }

    @Test
    public void testColumnNames() {

    }
}



