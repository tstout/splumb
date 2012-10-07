package splumb.core.host.plugin;

import com.google.common.util.concurrent.Service;
import org.junit.Before;
import org.mockito.Mock;
import org.xeustechnologies.jcl.JarClassLoader;
import splumb.common.plugin.PluginConfig;
import splumb.core.logging.HostLogger;

import java.io.File;
import java.util.Set;

import static com.google.common.collect.FluentIterable.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.*;

public class ComponentLoadTest {

    @Mock
    HostLogger logger;

    @Before
    public void setup() {
        initMocks(this);
        createJar();
    }

    //@Test
    public void loadPluginTest() throws Exception {

        ComponentLoader loader = new ComponentLoader(logger);
        JarClassLoader jarClassLoader = new JarClassLoader();

        jarClassLoader.add(String.format("%s/sampleplugin.jar", System.getProperty("user.home")));

        Set<Class<? extends Service>> services =
                loader.load("sampleplugin",
                        jarClassLoader);

        assertThat(services.size(), not(0));
        Service testService = from(services).first().get().newInstance();

        testService.startAndWait();
        assertThat(services.size(), not(0));
    }

    //@Test
    public void loadServiceConfigTest() throws IllegalAccessException, InstantiationException {

        ComponentLoader loader = new ComponentLoader(logger);
        JarClassLoader jarClassLoader = new JarClassLoader();

        // TOOD change this to use user.home instead fo working dir...
        // working dir causes a problem when running tests under gradle.


        String x = System.getProperty("user.home");
        jarClassLoader.add(String.format("%s/sampleplugin.jar", System.getProperty("user.home")));

//        ImmutableSet<Class<? extends PluginConfig>> serviceConfig =
//                loader.loadServiceConfig(jarClassLoader);

        Set<Class<? extends PluginConfig>> serviceConfig =
                loader.loadServiceConfig(Thread.currentThread().getContextClassLoader());

        PluginConfig testConfig = from(serviceConfig).first().get().newInstance();

        assertThat(testConfig.getServiceContext().basePackages().size(), not(0));
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

