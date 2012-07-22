package splumb.core.logging;


import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import splumb.common.logging.LogBus;
import splumb.core.db.SplumbDB;

public class DBLogService extends AbstractIdleService {

    private SplumbDB db;
    private LogBus bus;
    private DBLogSink sink;

    @Inject
    public DBLogService(LogBus bus, SplumbDB db) {
        this.bus = bus;
        this.db = db;
        sink = new DBLogSink(db);
    }

    @Override
    protected void startUp() throws Exception {
        db.create();
        bus.sub(sink);
    }

    @Override
    protected void shutDown() throws Exception {
        bus.unsub(sink);
    }
}
