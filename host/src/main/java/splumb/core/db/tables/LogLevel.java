package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public class LogLevel extends DBTable {

    public final DBTableColumn LOG_LEVEL_ID = addColumn("LOG_LEVEL_ID", DataType.INTEGER, 0, true);
    public final DBTableColumn NAME = addColumn("NAME", DataType.TEXT, 128, true);

    public LogLevel(DBDatabase db) {
        super("LOG_LEVEL", db);
        setPrimaryKey(LOG_LEVEL_ID);
    }
}
