package splumb.core.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import splumb.common.logging.AsyncBus;
import splumb.common.logging.LogBus;
import splumb.common.logging.LogConfig;

public class DevLoggingModule extends AbstractModule {

    @Override
    protected void configure() {
        //bind(LogPublisher.class).to(AbstractLogger.class);

        bind(LogBus.class).to(AsyncBus.class).in(Scopes.SINGLETON);
        bind(LogConfig.class).to(DBLogConfig.class).in(Scopes.SINGLETON);

        bindListener(Matchers.any(), new TypeListener() {
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {

                //
                //if (typeLiteral.getRawType() == AbstractLogger.class) {
                //System.out.printf("type is %s (Encounter name: %s)\n", typeLiteral.getRawType().getName(), typeEncounter.getClass().getName());
                //}

//                typeEncounter.register(new InjectionListener<I>() {
//                    public void afterInjection(I i) {
//                        System.out.println
//                        //eventBus.register(i);
//                    }
//                });
            }
        });
    }
}