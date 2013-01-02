package splumb.messaging;

import com.google.inject.AbstractModule;
import splumb.common.logging.LogPublisher;
import splumb.common.logging.NullLogger;

public class TestLogModule extends AbstractModule {
    //public TestLogModule() {}

    @Override
    protected void configure() {
        bind(LogPublisher.class).to(NullLogger.class);
    }
}
