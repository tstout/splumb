package splumb.core.db.tables;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import static splumb.common.db.Columns.*;

public class MsgBrokers extends DBTable {

    public final DBTableColumn HOST = varchar(this, "HOST", 128);
    public final DBTableColumn PORT = intCol(this, "PORT");
    public final DBTableColumn DESCR = nullableCharCol(this, "DESCR", 256);

    public MsgBrokers(DBDatabase db) {
        super("MSG_BROKERS", db);
        setPrimaryKey(HOST, PORT);
    }
}
