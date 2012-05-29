package splumb.core.db;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import org.h2.tools.Server;
import splumb.core.logging.LogPublisher;

import java.sql.SQLException;

class H2Db implements DBService {

    @Inject
    H2Db(LogPublisher logger) {
        this.logger = logger;
        service = new H2Service();
    }

    class H2Service extends AbstractIdleService {
        public H2Service() {
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

        Server h2Server;
    }

    @Override
    public DBService start() {
        service.startAndWait();
        return this;
    }

    @Override
    public void stop() {
        service.stopAndWait();
    }

    LogPublisher logger;
    H2Service service;

}
