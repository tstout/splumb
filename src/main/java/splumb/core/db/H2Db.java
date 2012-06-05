package splumb.core.db;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import org.h2.tools.Server;
import splumb.core.logging.LogPublisher;

import java.sql.SQLException;

class H2Db extends AbstractIdleService {

    Server h2Server;
    LogPublisher logger;

    @Inject
    H2Db(LogPublisher logger) {
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
