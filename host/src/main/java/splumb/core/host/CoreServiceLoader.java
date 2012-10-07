package splumb.core.host;

import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import com.google.inject.Injector;
import splumb.core.db.H2DBService;
import splumb.core.logging.DBLogService;
import splumb.core.logging.HostLogger;
import splumb.core.logging.LogService;

import static com.google.common.collect.ImmutableSet.of;

class CoreServiceLoader {
    private ShutdownActions shutdownActions;
    private HostLogger logger;

    @Inject
    public CoreServiceLoader(HostLogger logger, ShutdownActions shutdownActions) {
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
