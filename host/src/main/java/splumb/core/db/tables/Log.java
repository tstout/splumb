package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public class Log extends DBTable {

    public final DBTableColumn LogId = addColumn("LogId", DataType.AUTOINC, 0, true, "logIdSequence");
    public final DBTableColumn Level = addColumn("Level", DataType.INTEGER, 0, true);
    public final DBTableColumn DateTime = addColumn("DateTime", DataType.DATETIME, 0, true);
    public final DBTableColumn Msg = addColumn("Msg", DataType.TEXT, 2048, true);

    public Log(DBDatabase db) {
        super("Log", db);
        setPrimaryKey(LogId);
    }
}
