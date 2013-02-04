package splumb.tool;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

class SpToolInjectModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventBus.class).in(Scopes.SINGLETON);
    }
}
