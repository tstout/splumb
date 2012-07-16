package splumb.core.db;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import org.h2.tools.Server;
import splumb.common.logging.LogPublisher;

import java.sql.SQLException;

class H2DBService extends AbstractIdleService {

    private Server h2Server;
    private LogPublisher logger;

    @Inject
    public H2DBService(LogPublisher logger) {
        this.logger = logger;

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
