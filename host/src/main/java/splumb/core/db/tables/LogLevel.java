package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public class LogLevel extends DBTable {

    public final DBTableColumn LogLevelId;
    public final DBTableColumn Name;

    public LogLevel(DBDatabase db) {
        super("LogLevel", db);

        LogLevelId = addColumn("LogLevelId", DataType.INTEGER, 0, true);
        Name = addColumn("Name", DataType.TEXT, 128, true);

        setPrimaryKey(LogLevelId);
    }
}
