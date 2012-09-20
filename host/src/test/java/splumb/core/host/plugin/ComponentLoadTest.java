package splumb.core.host.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.xeustechnologies.jcl.JarClassLoader;
import splumb.common.plugin.ServiceConfig;
import splumb.core.logging.HostLogger;

import java.io.File;

import static com.google.common.collect.FluentIterable.from;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class ComponentLoadTest {

    @Mock
    HostLogger logger;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void loadPluginTest() throws Exception {
        createJar();

        ComponentLoader loader = new ComponentLoader(logger);
        JarClassLoader jarClassLoader = new JarClassLoader();

        jarClassLoader.add(String.format("%s/sampleplugin.jar", System.getProperty("user.dir")));

        ImmutableSet<Class<? extends Service>> services =
                loader.load("sampleplugin",
                        jarClassLoader);

        Service testService = from(services).first().get().newInstance();

        testService.startAndWait();
        assertThat(services.size(), not(0));
    }

    @Test
    public void loadServiceConfigTest() throws IllegalAccessException, InstantiationException {
        createJar();

        ComponentLoader loader = new ComponentLoader(logger);
        JarClassLoader jarClassLoader = new JarClassLoader();

        jarClassLoader.add(String.format("%s/sampleplugin.jar", System.getProperty("user.dir")));

        ImmutableSet<Class<? extends ServiceConfig>> serviceConfig =
                loader.loadServiceConfig(jarClassLoader);

        ServiceConfig testConfig = from(serviceConfig).first().get().newInstance();

        assertThat(testConfig.getServiceContext().getBaseScanPackage().size(), not(0));
        assertThat(serviceConfig.size(), not(0));
    }


    private void createJar() {
        String dir = System.getProperty("user.dir");

        File classDir = new File(dir, "classes/test/host/sampleplugin");
        new JarBuilder()
                .withStripFromPath(dir + "/classes/test/host")
                .withJarName("sampleplugin.jar")
                .withFile(classDir)
                .write()
                .withFile(new File(dir, "classes/test/host/splumb/plugin"))
                .write()
                .close();
    }

}

