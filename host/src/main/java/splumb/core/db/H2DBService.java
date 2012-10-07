package splumb.core.db;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.AbstractIdleService;
import joptsimple.OptionSet;
import org.h2.tools.Server;
import splumb.common.logging.LogPublisher;
import splumb.core.host.events.DbAvailableEvent;

import javax.inject.Inject;
import java.sql.SQLException;

public class H2DBService extends AbstractIdleService {

    private Server h2Server;
    private LogPublisher logger;
    private Impl implementation;
    private EventBus eventBus;

    @Inject
    public H2DBService(LogPublisher logger, OptionSet optionSet, EventBus eventBus) {
        this.logger = logger;
        this.eventBus = eventBus;
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
                eventBus.post(new DbAvailableEvent());
                logger.info("H2 Started -- Status:%s", h2Server.getStatus());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void stop() {
            h2Server.stop();
        }
    }
}
