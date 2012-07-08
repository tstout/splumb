package splumb.core.host;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import net.sf.extcos.ComponentQuery;
import net.sf.extcos.ComponentScanner;
import splumb.core.logging.LogPublisher;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

class ComponentLoader {

    private LogPublisher logger;
    private final Set<Class<? extends Service>> services = newHashSet();

    @Inject
    public ComponentLoader(LogPublisher logger) {
        this.logger = logger;
    }

    public ImmutableSet<Class<? extends Service>> load(final String basePackage) {
        logger.info("Scanning for components to load....");

        ComponentScanner scanner = new ComponentScanner();

        scanner.getClasses(new ComponentQuery() {
            protected void query() {
                select()
                    .from(basePackage)
                    .andStore(thoseImplementing(Service.class)
                            .into(services));
            }
        });

        logger.info("Found %d Components...", services.size());

        return ImmutableSet.copyOf(services);

//        for (Class clazz : services) {
//            logger.info("Found service %s", clazz.getName());
//        }
    }
}
