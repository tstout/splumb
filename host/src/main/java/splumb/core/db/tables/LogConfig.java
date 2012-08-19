package splumb.core.db.tables;

import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;

import static splumb.common.db.Columns.charCol;
import static splumb.common.db.Columns.nullableIntCol;

public class LogConfig extends DBTable {

    public final DBColumn LOGGER = charCol(this, "LOGGER", 128);
    public final DBColumn LOG_LEVEL = nullableIntCol(this, "LOG_LEVEL");

    public LogConfig(DBDatabase db) {
        super("LOG_CONFIG", db);

        setPrimaryKey(LOGGER);
    }
}
