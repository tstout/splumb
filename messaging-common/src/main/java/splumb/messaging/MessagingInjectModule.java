package splumb.messaging;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class MessagingInjectModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BrokerClient.class).in(Scopes.SINGLETON);
        bind(BrokerConfig.class).in(Scopes.SINGLETON);
        bind(MessageEndpoints.class).in(Scopes.SINGLETON);
        bind(EventBus.class).in(Scopes.SINGLETON);
    }
}
