package splumb.core.host;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Service;
import com.google.inject.Inject;
import net.sf.extcos.ComponentQuery;
import net.sf.extcos.ComponentScanner;
import splumb.core.logging.LogPublisher;

import java.util.HashSet;
import java.util.Set;

public class ComponentLoader extends AbstractIdleService {
    @Inject
    private LogPublisher logger;

    private final Set<Class<? extends Service>> services =
            new HashSet<Class<? extends Service>>();

    public ComponentLoader() {
    }

    @Override
    protected void startUp() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void shutDown() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void load() {
        logger.info("Scanning for components to load....");

        ComponentScanner scanner = new ComponentScanner();

        scanner.getClasses(new ComponentQuery() {
            protected void query() {
                select().from("splumb")
                        .andStore(thoseImplementing(Service.class)
                                .into(services));
            }
        });

        logger.info("Found %d Components...", services.size());
        for (Class clazz : services) {
            logger.info("Found service %s", clazz.getName());
        }
    }
}
