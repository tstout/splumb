package splumb.core.db.tables;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;

public class Log extends DBTable {

    public final DBTableColumn LOG_ID = autoIncCol(this, "LOG_ID");
    public final DBTableColumn LEVEL = intCol(this, "LEVEL");
    public final DBTableColumn DATE_TIME = dateTimeCol(this, "DATE_TIME");
    public final DBTableColumn MSG = charCol(this, "MSG", 8096);
    public final DBTableColumn LOGGER = charCol(this, "LOGGER", 256);
    public final DBTableColumn THREAD = charCol(this, "THREAD", 128);

    public Log(DBDatabase db) {
        super("LOG", db);
        setPrimaryKey(LOG_ID);
    }
}
