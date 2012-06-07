package splumb.core.db;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import org.h2.tools.Server;
import splumb.core.logging.LogPublisher;

import java.sql.SQLException;

class H2Db extends AbstractIdleService {

    private Server h2Server;
    private LogPublisher logger;

    @Inject
    public void setLogger(LogPublisher logger) {
        this.logger = logger;
    }

    public H2Db() {
        try {
            h2Server = Server.createTcpServer(new String[]{});
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void shutDown() throws Exception {
        h2Server.stop();
        logger.info("H2 service shutdown complete");
    }

    @Override
    protected void startUp() throws Exception {
        try {
            h2Server.start();
            logger.info("H2 Started: %s", h2Server.getStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
