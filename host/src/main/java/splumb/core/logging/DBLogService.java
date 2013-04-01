package splumb.core.logging;


import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import splumb.common.db.DBDriver;
import splumb.common.db.schema.Schema;
import splumb.common.logging.LogBus;
import splumb.core.db.SplumbDB;

public class DBLogService extends AbstractIdleService {

    private SplumbDB db;
    private LogBus bus;
    private DBLogSink sink;
    private DBDriver driver;

    @Inject
    public DBLogService(LogBus bus, SplumbDB db, DBDriver driver) {
        this.bus = bus;
        this.db = db;
        sink = new DBLogSink(db);
        this.driver = driver;
    }

    @Override
    protected void startUp() throws Exception {
        bus.sub(sink);
        db.create();

        //
        // init schema mgmt-related tables if necessary
        // TODO - longterm, this might not be the best location for this code.
        //
        //
        new Schema(driver).createMgmtTables();
    }

    @Override
    protected void shutDown() throws Exception {
        bus.unsub(sink);
    }
}
