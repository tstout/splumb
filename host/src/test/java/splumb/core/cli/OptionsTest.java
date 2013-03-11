package splumb.core.cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

public class OptionsTest {

    @Inject
    OptValues values;

    @Test
    public void parseTest() {
        String[] args = new String[] {"-h", "-nodb", "-jmx 5000"};

        Injector injector = Guice.createInjector(new CliModule(args));
        injector.injectMembers(this);

        assertFalse(values.dropTables());
        assertTrue(values.jmxPort() == 5000);
    }
}
