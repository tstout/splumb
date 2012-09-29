package splumb.core.host.plugin;

import org.xeustechnologies.jcl.JarClassLoader;
import splumb.core.logging.HostLogger;

import javax.inject.Inject;
import java.io.File;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class PluginLoader {

    private HostLogger logger;
    private Map<String, Plugin> classLoaders = newHashMap();

    @Inject
    public PluginLoader(HostLogger logger) {
        this.logger = logger;
    }

    public void load() {
        PluginDir dir = new PluginDir();

        logger.info("Attempting to load plugins from %s", dir.path());

        for (File jarFile : dir.getJarFiles()) {

        }
    }
}

class Plugin {
    Class impl;
    String name;
    JarClassLoader classLoader;
}