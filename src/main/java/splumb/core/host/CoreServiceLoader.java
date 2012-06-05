package splumb.core.host;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;

class CoreServiceLoader {
    private ComponentLoader loader;

    public CoreServiceLoader(ComponentLoader loader) {
        this.loader = loader;
    }

    public ImmutableSet<Class<? extends Service>> load() {
        return ImmutableSet.copyOf(loader.load("splumb.core"));
    }
}
