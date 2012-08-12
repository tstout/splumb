package splumb.core.host;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import splumb.common.logging.LogPublisher;
import splumb.core.db.DBDevModule;
import splumb.core.logging.DevLoggingModule;

public class Host {

    private LogPublisher logger;

    public Host() {}

    @Inject
    public void setLogger(LogPublisher logger) {
        this.logger = logger;
    }

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(
                new DevLoggingModule(),
                new DBDevModule(),
                new DevInjectModule(args));

        injector.getInstance(Host.class).start(injector);
    }

    void start(Injector injector) {
        CoreServiceLoader loader =
                injector.getInstance(CoreServiceLoader.class)
                        .load(injector);

        logger.info("host Initialization Complete");
        loader.waitForTerm();
    }
}
