package splumb.core.db;

import com.google.inject.AbstractModule;

public class DBTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBDriver.class).to(H2InMemDriver.class);
    }
}
