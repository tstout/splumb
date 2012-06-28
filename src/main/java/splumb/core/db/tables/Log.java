package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public class Log extends DBTable {

    public final DBTableColumn logId;
    public final DBTableColumn level;
    public final DBTableColumn dateTime;
    public final DBTableColumn msg;

    public Log(DBDatabase db) {
        super("Log", db);

        logId = addColumn("logId", DataType.AUTOINC, 0, true, "logIdSequence");
        dateTime = addColumn("dateTime", DataType.DATETIME, 0, true);
        level = addColumn("level", DataType.INTEGER, 0, true);
        msg = addColumn("msg", DataType.TEXT, 2048, true);

        setPrimaryKey(logId);
    }
}
