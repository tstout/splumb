package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public class Log extends DBTable {

    public final DBTableColumn LOG_ID = addColumn("LOG_ID", DataType.AUTOINC, 0, true);
    public final DBTableColumn LEVEL = addColumn("LEVEL", DataType.INTEGER, 0, true);
    public final DBTableColumn DATE_TIME = addColumn("DATE_TIME", DataType.DATETIME, 0, true);
    public final DBTableColumn MSG = addColumn("MSG", DataType.TEXT, 8096, true);
    public final DBTableColumn LOG_SOURCE_ID = addColumn("LOG_SOURCE_ID", DataType.INTEGER, 0, true);

    public Log(DBDatabase db) {
        super("LOG", db);
        setPrimaryKey(LOG_ID);
    }
}
