package splumb.messaging;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class MessagingServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BrokerClient.class).in(Scopes.SINGLETON);
        bind(BrokerConfig.class).to(DbBrokerConfig.class).in(Scopes.SINGLETON);
        bind(MessageEndpoints.class).in(Scopes.SINGLETON);
        bind(EventBus.class).in(Scopes.SINGLETON);
        //bind(AdminMsgRouter.class).in(Scopes.SINGLETON);
    }
}
