package splumb.core.db.tables;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.varchar;
import static splumb.common.db.Columns.nullableIntCol;

public class LogConfig extends DBTable {

    public final DBTableColumn LOGGER = varchar(this, "LOGGER", 128);
    public final DBTableColumn LOG_LEVEL = nullableIntCol(this, "LOG_LEVEL");

    public LogConfig(DBDatabase db) {
        super("LOG_CONFIG", db);

        setPrimaryKey(LOGGER);
    }
}
