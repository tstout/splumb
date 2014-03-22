package splumb.core.host;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import db.io.Database;
import db.io.config.DBCredentials;
import db.io.h2.H2Credentials;
import db.io.h2.H2Db;
import splumb.core.db.SplumbDB;

class HostInjectModule extends AbstractModule {

    private String[] args;
    //private OptionSet optionSet;
    private EventBus eventBus;

    //@Inject
    //public HostInjectModule(OptValues optValues) {
    public HostInjectModule() {
        //this.optionSet = optionSet;
        eventBus = new EventBus();
    }

    @Override
    protected void configure() {
        //bind(OptionSet.class).toInstance(optionSet);
        bind(Database.class).to(H2Db.class).in(Scopes.SINGLETON);
        bind(DBCredentials.class).toInstance(H2Credentials.h2LocalServerCreds("splumb", "~/.splumb/db"));
        bind(EventBus.class).toInstance(eventBus);
        bind(SplumbDB.class).in(Scopes.SINGLETON);
        bind(ShutdownActions.class).in(Scopes.SINGLETON);

        //
        // Setup the event bus....
        //
        bindListener(Matchers.any(), new TypeListener() {
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {
                    public void afterInjection(I i) {
                        eventBus.register(i);
                    }
                });
            }
        });
    }
}