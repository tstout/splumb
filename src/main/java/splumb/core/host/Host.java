package splumb.core.host;


public class Host {
    public static void main(String[] args)
    {
        new Host(args);
    }

    Host(String[] args)
    {
        OptCollection opts = new OptCollection();

        opts.add(new PortOpt(),
                new ProcIDOpt(),
                new HelpOpt())
                .processOpts(args);

        injectModules.add(new DevInjectModule());
        Injector injector = Guice.createInjector(injectModules);

        injector.getInstance(LogBus.class).sub(new ConsoleLogSink());
        injector.injectMembers(this);

        final DBService db = injector.getInstance(DBService.class).start();
        logger.info(" host Initialization Complete");

        new ShutdownActions()
                .add(new Runnable()
                {
                    public void run() { db.stop(); }
                },
                        "H2")
                .install()
                .waitForTermination();
    }

    @Inject
    private LogPublisher logger;

    private List<AbstractModule> injectModules =
            new ArrayList<AbstractModule>()
            {
                {
                    add(new DevLoggingModule());
                    add(new DBDevModule());
                }
            };


    //private Map<>

    class PortOpt extends AbstractOptAction
    {
        public static final String OPT = "port";

        public PortOpt()
        {
            setOption(new OptBuilder()
                    .withArgName(OPT).hasArg()
                    .withDescription("controller socket port")
                    .create(OPT));
        }

        @Override
        public void Run(String arg, OptCollection registry)
        {
            //new ParentConn(Integer.parseInt(arg)).start();
        }
    }

    class ProcIDOpt extends AbstractOptAction
    {
        public static final String OPT = "ID";

        public ProcIDOpt()
        {
            setOption(new OptBuilder()
                    .withArgName(OPT).hasArg()
                    .withDescription("Unique process ID")
                    .create(OPT));
        }

        @Override
        public void Run(String arg, OptCollection registry)
        {
        }
    }

    class HelpOpt extends AbstractOptAction
    {
        public HelpOpt()
        {
            setOption(new OptBuilder()
                    .withDescription("Show Help")
                    .create("h"));
        }

        @Override
        public void Run(String arg, OptCollection registry)
        {
            registry.showDescr("sphost");
        }
    }
}
