package splumb.core.db.tables;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;

public class LogSource extends DBTable {

    public final DBTableColumn LOG_SOURCE_ID = autoInc(this, "LOGGER");
    public final DBTableColumn SOURCE = addColumn("SOURCE", DataType.TEXT, 256, true);

    public LogSource(DBDatabase db) {
        super("LOGGER", db);

        setPrimaryKey(LOG_SOURCE_ID);
    }
}
