package splumb.messaging;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

public abstract class MessagingClientModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BrokerClient.class).in(Scopes.SINGLETON);
        bind(BrokerConfig.class).to(ClientBrokerConfig.class).in(Scopes.SINGLETON);
        bind(MessageEndpoints.class).in(Scopes.SINGLETON);
        bind(EventBus.class).in(Scopes.SINGLETON);

        bindConstant().annotatedWith(Names.named("brokerPort")).to(port());
        bindConstant().annotatedWith(Names.named("brokerHost")).to(host());
        //bind(AdminMsgRouter.class).in(Scopes.SINGLETON);
    }

    protected abstract int port();
    protected abstract String host();

}
