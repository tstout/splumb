package splumb.core.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import splumb.common.logging.AsyncLogBus;
import splumb.common.logging.LogBus;
import splumb.common.logging.LogConfig;
import splumb.common.logging.LogPublisher;

public class DevLoggingModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(LogBus.class).to(AsyncLogBus.class).in(Scopes.SINGLETON);
        bind(LogConfig.class).to(DBLogConfig.class).in(Scopes.SINGLETON);
        bind(LogPublisher.class).to(HostLogger.class).in(Scopes.SINGLETON);
    }
}