package splumb.messaging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class MessagingInjectModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RemoteBroker.class).in(Scopes.SINGLETON);
        bind(BrokerConfig.class).in(Scopes.SINGLETON);
        bind(MessageEndpoints.class).in(Scopes.SINGLETON);
    }
}
