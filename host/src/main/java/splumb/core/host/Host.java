package splumb.core.host;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import joptsimple.OptionParser;
import splumb.core.cli.AbstractOptAction;
import splumb.core.cli.OptBuilder;
import splumb.core.cli.OptCollection;
import splumb.core.db.DBDevModule;
import splumb.core.db.SplumbDB;
import splumb.core.logging.DBLogSink;
import splumb.core.logging.DevLoggingModule;
import splumb.core.logging.LogBus;
import splumb.common.logging.LogPublisher;

public class Host {

    private LogPublisher logger;

    @Inject
    public void setLogger(LogPublisher logger) {
        this.logger = logger;
    }

    public static void main(String[] args) {
        new Host(args);
    }

    Host(String[] args) {

        OptionParser parser = new OptionParser();
        parser.accepts("nodb", "Run without database");

        Injector injector = Guice.createInjector(
                new DevLoggingModule(),
                new DBDevModule(),
                new DevInjectModule(args));

        LogBus logBus = injector.getInstance(LogBus.class);
        logBus.sub(new ConsoleLogSink());

        injector.injectMembers(this);

        //
        // Start any core services...
        //
        CoreServiceLoader loader =
                injector.getInstance(CoreServiceLoader.class)
                        .load(injector);

        injector.getInstance(SplumbDB.class).create();
        logBus.sub(injector.getInstance(DBLogSink.class));

        logger.info("host Initialization Complete");
        loader.waitForTerm();
    }
}
