package splumb.core.host;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Service;
import com.google.inject.*;
import splumb.core.cli.AbstractOptAction;
import splumb.core.cli.OptBuilder;
import splumb.core.cli.OptCollection;
import splumb.core.db.DBDevModule;
import splumb.core.db.DBService;
import splumb.core.logging.DevLoggingModule;
import splumb.core.logging.LogBus;
import splumb.core.logging.LogPublisher;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Host {

    public static void main(String[] args) {
        new Host(args);
    }

    Host(String[] args) {
        OptCollection opts = new OptCollection();

        opts.add(new PortOpt(),
                new ProcIDOpt(),
                new HelpOpt())
                .processOpts(args);

        //injectModules.add(new DevInjectModule());
        Injector injector = Guice.createInjector(
                new DevLoggingModule(),
                new DBDevModule(),
                new DevInjectModule());

        injector.getInstance(LogBus.class).sub(new ConsoleLogSink());
        injector.injectMembers(this);

        //
        // Start any core services...
        //
        CoreServiceLoader coreLoader =
                new CoreServiceLoader(injector.getInstance(ComponentLoader.class));


        ShutdownActions actions = new ShutdownActions();
        for (Class<? extends Service> service : coreLoader.load()) {
            try {
                Service coreService = service.newInstance();
                injector.injectMembers(coreService);
                actions.add(coreService);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        logger.info("host Initialization Complete");
        actions.install().waitForTermination();

//        new ShutdownActions()
//                .add(new Runnable() {
//                    public void run() {
//                        db.stop();
//                    }
//                },
//                        "H2")
//                .install()
//                .waitForTermination();
    }

    @Inject
    private LogPublisher logger;

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
