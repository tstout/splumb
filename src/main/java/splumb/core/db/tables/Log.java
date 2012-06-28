package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public class Log extends DBTable {

    public final DBTableColumn LogId;
    public final DBTableColumn Level;
    public final DBTableColumn DateTime;
    public final DBTableColumn Msg;

    public Log(DBDatabase db) {
        super("Log", db);

        //
        // BAH! don't like this...consider a coustom wrapper to hide this ugliness..
        //
        LogId = addColumn("LogId", DataType.AUTOINC, 0, true, "logIdSequence");
        DateTime = addColumn("DateTime", DataType.DATETIME, 0, true);
        Level = addColumn("Level", DataType.INTEGER, 0, true);
        Msg = addColumn("Msg", DataType.TEXT, 2048, true);

        setPrimaryKey(LogId);
    }
}
