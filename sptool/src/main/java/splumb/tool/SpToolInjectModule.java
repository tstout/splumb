package splumb.tool;

import com.google.common.eventbus.EventBus;
import com.google.inject.Scopes;
import splumb.common.logging.LogPublisher;
import splumb.common.logging.NullLogger;
import splumb.messaging.MessagingClientModule;

class SpToolInjectModule extends MessagingClientModule {
    @Override
    protected void configure() {
        super.configure();
        bind(EventBus.class).in(Scopes.SINGLETON);
        bind(LogPublisher.class).to(NullLogger.class);
    }

    @Override
    protected int port() {
        return 8000;
    }

    @Override
    protected String host() {
        return "127.0.0.1";
    }
}
