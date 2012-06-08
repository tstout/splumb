package splumb.core.host;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.lang.reflect.Constructor;

class CoreServiceLoader {
    private ComponentLoader loader;
    private ShutdownActions shutdownActions;

    @Inject
    public CoreServiceLoader(ComponentLoader loader, ShutdownActions shutdownActions) {
        this.loader = loader;
        this.shutdownActions = shutdownActions;
    }

    public CoreServiceLoader load(Injector injector) {

        for (Class<? extends Service> service : loader.load("splumb.core")) {
            try {
                Service coreService = injector.getInstance(service);
                shutdownActions.add(coreService);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        shutdownActions.install();
        return this;
    }

    public void waitForTerm() {
        shutdownActions.waitForTermination();
    }
}
