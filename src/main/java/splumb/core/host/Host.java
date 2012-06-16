package splumb.core.host;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import splumb.core.cli.AbstractOptAction;
import splumb.core.cli.OptBuilder;
import splumb.core.cli.OptCollection;
import splumb.core.db.DBDevModule;
import splumb.core.db.SplumbDB;
import splumb.core.logging.DBLogSink;
import splumb.core.logging.DevLoggingModule;
import splumb.core.logging.LogBus;
import splumb.core.logging.LogPublisher;

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
        OptCollection opts = new OptCollection();

        opts.add(new PortOpt(),
                new ProcIDOpt(),
                new HelpOpt())
                .processOpts(args);

        Injector injector = Guice.createInjector(
                new DevLoggingModule(),
                new DBDevModule(),
                new DevInjectModule());

        LogBus logBus = injector.getInstance(LogBus.class);
        logBus.sub(new ConsoleLogSink());
        logBus.sub(injector.getInstance(DBLogSink.class));
        injector.injectMembers(this);

        //
        // Start any core services...
        //
        CoreServiceLoader loader =
                injector.getInstance(CoreServiceLoader.class)
                        .load(injector);

        logger.info("host Initialization Complete");

        SplumbDB.create();
        loader.waitForTerm();
    }



    class PortOpt extends AbstractOptAction {
        public static final String OPT = "port";

        public PortOpt() {
            setOption(new OptBuilder()
                    .withArgName(OPT).hasArg()
                    .withDescription("controller socket port")
                    .create(OPT));
        }

        @Override
        public void Run(String arg, OptCollection registry) {
            //new ParentConn(Integer.parseInt(arg)).start();
        }
    }

    class ProcIDOpt extends AbstractOptAction {
        public static final String OPT = "ID";

        public ProcIDOpt() {
            setOption(new OptBuilder()
                    .withArgName(OPT).hasArg()
                    .withDescription("Unique process ID")
                    .create(OPT));
        }

        @Override
        public void Run(String arg, OptCollection registry) {
        }
    }

    class HelpOpt extends AbstractOptAction {
        public HelpOpt() {
            setOption(new OptBuilder()
                    .withDescription("Show Help")
                    .create("h"));
        }

        @Override
        public void Run(String arg, OptCollection registry) {
            registry.showDescr("sphost");
        }
    }
}
