package splumb.messaging;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;
import splumb.common.logging.LogPublisher;
import splumb.common.logging.NullLogger;
import splumb.core.cli.OptValues;
import splumb.core.db.SplumbDB;
import splumb.core.events.DbAvailableEvent;

public class BrokerTestModule extends AbstractModule {
    @Override
    protected void configure() {

        H2InMemDriver driver = new H2InMemDriver();

        OptValues optValues = new OptValues() {

            @Override
            public int jmxPort() {
                return 0;
            }

            @Override
            public boolean noDB() {
                return false;
            }

            @Override
            public boolean dropTables() {
                return false;
            }
        };

        EventBus bus = new EventBus();

        bind(DBDriver.class).toInstance(driver);
        bind(EventBus.class).toInstance(bus);
        bind(LogPublisher.class).to(NullLogger.class).in(Scopes.SINGLETON);

        SplumbDB db = new SplumbDB(driver, optValues, bus, new NullLogger());
        db.init(new DbAvailableEvent());
        db.create();

        bind(SplumbDB.class).toInstance(db);

        bind(BrokerClient.class).in(Scopes.SINGLETON);
        bind(BrokerConfig.class).to(ClientBrokerConfig.class).in(Scopes.SINGLETON);
        bind(MessageEndpoints.class).in(Scopes.SINGLETON);

        bindConstant()
                .annotatedWith(Names.named("brokerPort"))
                .to(8000);

        bindConstant()
                .annotatedWith(Names.named("brokerHost"))
                .to("127.0.0.1");
    }
}
