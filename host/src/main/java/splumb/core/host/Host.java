package splumb.core.host;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Injector;
import splumb.common.logging.LogPublisher;
import splumb.core.cli.CliModule;
import splumb.core.db.DBDevModule;
import splumb.core.events.HostDbTablesAvailableEvent;
import splumb.core.host.plugin.PluginLoader;
import splumb.core.logging.DevLoggingModule;

import javax.inject.Inject;

// TODO - have an option that starts up the host with jmx connectivity enabled for tools such as jconsole.
public class Host {

    private LogPublisher logger;
    private Injector injector;

    public Host() {}

    @Inject
    public void setLogger(LogPublisher logger) {
        this.logger = logger;
    }

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(
                new DevLoggingModule(),
                new DBDevModule(),
                new HostInjectModule(),
                new CliModule(args));

        injector.getInstance(Host.class).start(injector);
    }

    @Subscribe
    public void tablesAvailable(HostDbTablesAvailableEvent event) {
        //
        // Don't load plugins until DB is available.
        //
        injector.getInstance(PluginLoader.class)
                .loadConfigurations()
                .loadServices(injector)
                .startServices();

        logger.info("host Initialization Complete");
    }

    void start(Injector injector) {
        this.injector = injector;
        //
        // Fire up any intrinsic services...
        //
        CoreServiceLoader loader =
                injector.getInstance(CoreServiceLoader.class)
                        .load(injector);

        loader.waitForTerm();
    }
}
