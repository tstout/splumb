package splumb.core.host.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import net.sf.extcos.ComponentQuery;
import net.sf.extcos.ComponentScanner;
import splumb.common.logging.LogPublisher;
import splumb.common.plugin.PluginConfig;
import splumb.core.logging.HostLogger;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

class ComponentLoader {

    private LogPublisher logger;
    private final Set<Class<? extends Service>> services = newHashSet();
    private final Set<Class<? extends PluginConfig>> serviceConfig = newHashSet();

    @Inject
    public ComponentLoader(HostLogger logger) {
        this.logger = logger;
    }

    public ImmutableSet<Class<? extends Service>> load(final String basePackage) {
        return load(basePackage, Thread.currentThread().getContextClassLoader());
    }

    public ImmutableSet<Class<? extends PluginConfig>> loadServiceConfig(ClassLoader classLoader) {

        ComponentScanner scanner = new ComponentScanner();

        scanner.getClasses(new ComponentQuery() {
            protected void query() {
                select()
                        .from("splumb.plugin")
                        .andStore(thoseImplementing(PluginConfig.class)
                                .into(serviceConfig));
            }
        }, classLoader);

        logger.info("Found %d Components...", services.size());

        return ImmutableSet.copyOf(serviceConfig);
    }

    public ImmutableSet<Class<? extends Service>> load(final String basePackage, ClassLoader classLoader) {
        logger.info("Scanning for components to load from %s....", basePackage);

        ComponentScanner scanner = new ComponentScanner();

        scanner.getClasses(new ComponentQuery() {
            protected void query() {
                select()
                        .from(basePackage)
                        .andStore(thoseImplementing(Service.class)
                                .into(services));
            }
        }, classLoader);

        logger.info("Found %d Components...", services.size());

        return ImmutableSet.copyOf(services);
    }
}
