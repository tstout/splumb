package splumb.core.host;

import com.google.inject.Guice;
import com.google.inject.Injector;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import splumb.core.db.DBDevModule;
import splumb.core.host.plugin.PluginLoader;
import splumb.core.logging.DevLoggingModule;
import splumb.core.logging.HostLogger;

import javax.inject.Inject;

import static java.util.Arrays.*;

public class Host {

    private HostLogger logger;

    public Host() {}

    @Inject
    public void setLogger(HostLogger logger) {
        this.logger = logger;
    }

    public static void main(String[] args) throws Exception {

        OptionParser parser = new OptionParser();
        parser.accepts("nodb", "Run without DB");
        parser.accepts("droptables", "drop and recreate tables at startup");
        parser.acceptsAll(asList("help", "h"), "Show help");

        OptionSet optionSet = parser.parse(args);

        if (optionSet.has("help")) {
            parser.printHelpOn(System.out);
            System.exit(0);
        }

        Injector injector = Guice.createInjector(
                new DevLoggingModule(),
                new DBDevModule(),
                new HostInjectModule(optionSet));

        injector.getInstance(Host.class).start(injector);
    }

    void start(Injector injector) {
        //
        // Fire up any intrinsic services...
        //
        CoreServiceLoader loader =
                injector.getInstance(CoreServiceLoader.class)
                        .load(injector);
        //
        // Load up any plugins...
        //
        injector.getInstance(PluginLoader.class).loadConfigurations();

        logger.info("host Initialization Complete");
        loader.waitForTerm();
    }
}
