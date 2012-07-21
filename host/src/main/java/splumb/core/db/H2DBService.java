package splumb.core.db;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.inject.Inject;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.h2.tools.Server;
import splumb.common.logging.LogPublisher;

import java.sql.SQLException;

class H2DBService extends AbstractIdleService {

    private Server h2Server;
    private LogPublisher logger;
    private Impl implementation;

    @Inject
    public H2DBService(LogPublisher logger, OptionSet optionSet) {
        this.logger = logger;
        implementation = optionSet.has("nodb") ? new Impl() : new ActiveImpl();
    }

    @Override
    protected void shutDown() throws Exception {
        implementation.stop();
    }

    @Override
    protected void startUp() throws Exception {
        implementation.start();
    }

    class Impl {
        public void start() {
        }

        public void stop() {
        }
    }

    class ActiveImpl extends Impl {
        public void start() {

            try {
                h2Server = Server.createTcpServer(new String[]{});
                h2Server.start();
                logger.info("H2 Started: %s", h2Server.getStatus());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void stop() {
            h2Server.stop();
            logger.info("H2 service shutdown complete");
        }
    }
}
