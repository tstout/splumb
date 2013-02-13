package splumb.core.host;

import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import com.google.inject.Injector;
import splumb.common.logging.LogPublisher;
import splumb.core.db.H2DBService;
import splumb.core.logging.DBLogService;
import splumb.core.logging.LogService;

import static com.google.common.collect.ImmutableSet.*;

/**
 * Loads the core services that are intrinsic to splumb host. This includes Logging, Database, and
 * soon Messaging.
 */
class CoreServiceLoader {
    private ShutdownActions shutdownActions;
    private LogPublisher logger;

    @Inject
    public CoreServiceLoader(LogPublisher logger, ShutdownActions shutdownActions) {
        this.shutdownActions = shutdownActions;
        this.logger = logger;
    }

    public CoreServiceLoader load(Injector injector) {

        // todo - why is there a LogService and DBLogService...why not just LogService?
        for (Class<? extends Service> service : of(LogService.class, H2DBService.class, DBLogService.class)) {

            Service coreService = injector.getInstance(service);

            shutdownActions.add(coreService);
            coreService.startAndWait();
        }

//        for (Class<? extends Service> service : loader.loadConfigurations("splumb.core")) {
//            try {
//                Service coreService = injector.getInstance(service);
//                shutdownActions.add(coreService);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }

        shutdownActions.install();
        return this;
    }

    public void waitForTerm() {
        shutdownActions.waitForTermination();
    }
}
