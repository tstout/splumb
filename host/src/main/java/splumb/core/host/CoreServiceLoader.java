package splumb.core.host;

import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import com.google.inject.Injector;
import splumb.core.db.H2DBService;
import splumb.core.logging.DBLogService;
import splumb.core.logging.LogService;

import static com.google.common.collect.ImmutableSet.of;

class CoreServiceLoader {
    //private ComponentLoader loader;
    private ShutdownActions shutdownActions;

    @Inject
    public CoreServiceLoader(ShutdownActions shutdownActions) {
        //this.loader = loader;
        this.shutdownActions = shutdownActions;
    }

    public CoreServiceLoader load(Injector injector) {

        for (Class<? extends Service> service : of(H2DBService.class, LogService.class, DBLogService.class)) {

            Service coreService = injector.getInstance(service);

            shutdownActions.add(coreService);
            coreService.startAndWait();
        }

//        for (Class<? extends Service> service : loader.load("splumb.core")) {
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
