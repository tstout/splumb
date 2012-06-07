package splumb.core.host;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.lang.reflect.Constructor;

class CoreServiceLoader {
    private ComponentLoader loader;

    @Inject
    public CoreServiceLoader(ComponentLoader loader) {
        this.loader = loader;
    }

    public void load(Injector injector, ShutdownActions actions) {

        for (Class<? extends Service> service : loader.load("splumb.core")) {
            try {
                Constructor con = service.getDeclaredConstructor();
                con.setAccessible(true);

                Service coreService = (Service)con.newInstance();
                injector.injectMembers(coreService);
                actions.add(coreService);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
