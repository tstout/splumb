package splumb.core.host.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.xeustechnologies.jcl.JarClassLoader;
import splumb.core.logging.HostLogger;

import java.io.File;
import java.io.FileNotFoundException;

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
    public void loadPluginTest() throws FileNotFoundException {
        createJar();

        ComponentLoader loader = new ComponentLoader(logger);
        JarClassLoader jarClassLoader = new JarClassLoader();

        jarClassLoader.add(String.format("%s/", System.getProperty("user.dir")));

        ImmutableSet<Class<? extends Service>> services =
                loader.load("sampleplugin",
                        jarClassLoader);

        assertThat(services.size(), not(0));
    }

    private void createJar() {
        String dir = System.getProperty("user.dir");

        File classDir = new File(dir, "classes/test/host/sampleplugin");
        new JarBuilder()
                .withBasePath(dir + "/classes/test/host")
                .withJarName("sampleplugin.jar")
                .withFile(classDir)
                .build();
    }

}

