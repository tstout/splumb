package splumb.core.db.tables;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.Schema;

import static splumb.common.db.Columns.*;

public class MsgBrokers extends DBTable implements Schema {

    public final DBTableColumn HOST = charCol(this, "HOST", 128);
    public final DBTableColumn PORT = intCol(this, "PORT");
    public final DBTableColumn DESCR = nullableCharCol(this, "DESCR", 256);

    public MsgBrokers(DBDatabase db) {
        super("MSG_BROKERS", db);
        setPrimaryKey(HOST, PORT);
    }

    @Override
    public void create() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
