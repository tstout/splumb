package splumb.core.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import splumb.common.logging.AsyncBus;
import splumb.common.logging.LogBus;
import splumb.common.logging.LogConfig;

public class DevLoggingModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(LogBus.class).to(AsyncBus.class).in(Scopes.SINGLETON);
        bind(LogConfig.class).to(DBLogConfig.class).in(Scopes.SINGLETON);
        bind(HostLogger.class).in(Scopes.SINGLETON);
    }
}