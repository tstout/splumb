package splumb.core.host.plugin;

import com.google.common.util.concurrent.Service;
import splumb.common.plugin.PluginConfig;
import splumb.common.plugin.PluginName;
import splumb.core.host.ShutdownActions;
import splumb.core.logging.HostLogger;

import javax.inject.Inject;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;
import static java.lang.String.*;

public class PluginLoader {

    private HostLogger logger;
    private Map<PluginName, Plugin> plugins = newHashMap();
    private ComponentLoader loader;
    private ShutdownActions shutdownActions;

    @Inject
    public PluginLoader(HostLogger logger, ShutdownActions shutdownActions) {
        this.logger = logger;
        loader = new ComponentLoader(logger);
        this.shutdownActions = shutdownActions;
    }

    public PluginLoader loadConfigurations() {
        PluginDir dir = new PluginDir();

        logger.info("Attempting to load configurations plugins from %s", dir.path());

        for (File jarFile : dir.jarFiles()) {

            ClassLoader classLoaderForJar = newClassLoader(dir, jarFile.getName());

            for (Class<? extends PluginConfig> clazz : loader.loadServiceConfig(classLoaderForJar)) {
                Plugin newPlugin = new Plugin();
                newPlugin.classLoader = classLoaderForJar;

                try {
                    newPlugin.config = clazz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                plugins.put(newPlugin.config.getServiceContext().name(), newPlugin);
            }
        }

        logger.info("Found %d plugin configuration%s", plugins.size(), plugins.size() == 1 ? "" : "s");
        return this;
    }

    public PluginLoader loadServices() {

        for (Plugin plugin : plugins.values()) {
            for (String basePackage : plugin.config.getServiceContext().basePackages()) {
                plugin.serviceClasses.addAll(loader.load(basePackage, plugin.classLoader));
            }
        }

        instantiateServices();
        return this;
    }

    private void instantiateServices() {
        for (Plugin plugin : plugins.values()) {
            for (Class clazz : plugin.serviceClasses) {
                try {
                    Service newService = (Service)clazz.newInstance();
                    shutdownActions.add(newService);
                    plugin.serviceInstances.add(newService);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void startServices() {
        for (Plugin plugin : plugins.values()) {
            for (Service service : plugin.serviceInstances) {
                service.startAndWait();
            }
        }
    }

//    public void stopServices() {
//        for (Plugin plugin : plugins.values()) {
//            for (Service service: plugin.serviceInstances) {
//                service.stopAndWait();
//            }
//        }
//    }

    private URLClassLoader newClassLoader(PluginDir dir, String jarFile) {
        URL jarUrl;

        try {
            jarUrl = new URL("jar", "", format("file:%s/%s!/", dir.path(), jarFile));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return new URLClassLoader(new URL[]{jarUrl}, getClass().getClassLoader());
    }
}

class Plugin {
    PluginConfig config;
    ClassLoader classLoader;
    Set<Class<? extends Service>> serviceClasses = newHashSet();
    Set<Service> serviceInstances = newHashSet();
}