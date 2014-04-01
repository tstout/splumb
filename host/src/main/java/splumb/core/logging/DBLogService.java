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
    public DBLogService(LogBus bus, SplumbDB db, DBLogSink sink) {
        this.bus = bus;
        this.db = db;
        this.sink = sink;
    }

    @Override
    protected void startUp() throws Exception {
        bus.sub(sink);
        db.create();

    }

    @Override
    protected void shutDown() throws Exception {
        bus.unsub(sink);
    }
}
