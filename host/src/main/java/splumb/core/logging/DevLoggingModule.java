package splumb.core.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class DevLoggingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LogPublisher.class).to(Logger.class);

        bind(LogBus.class).to(AsyncBus.class).in(Scopes.SINGLETON);
    }
}