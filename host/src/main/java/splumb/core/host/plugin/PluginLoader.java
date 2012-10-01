package splumb.core.host.plugin;

import splumb.common.plugin.PluginConfig;
import splumb.common.plugin.PluginName;
import splumb.core.logging.HostLogger;

import javax.inject.Inject;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import static com.google.common.collect.Maps.*;

public class PluginLoader {

    private HostLogger logger;
    private Map<PluginName, Plugin> plugins = newHashMap();
    private ComponentLoader loader;

    @Inject
    public PluginLoader(HostLogger logger) {
        this.logger = logger;
        loader = new ComponentLoader(logger);
    }

    public Map<PluginName, Plugin> loadConfigurations() {
        PluginDir dir = new PluginDir();

        logger.info("Attempting to load configurations plugins from %s", dir.path());

        for (File jarFile : dir.getJarFiles()) {

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

        logger.info("Found %d plugin configurations", plugins.size());
        return plugins;
    }

    private URLClassLoader newClassLoader(PluginDir dir, String jarFile) {
        URL jarUrl;

        try {
            jarUrl = new URL("jar", "","file:" + dir.path() + "/" + jarFile + "!/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return new URLClassLoader(new URL[] {jarUrl}, getClass().getClassLoader());
    }
}

class Plugin {
    PluginConfig config;
    ClassLoader classLoader;
}