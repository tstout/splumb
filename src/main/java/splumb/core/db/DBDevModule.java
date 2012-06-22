package splumb.core.db;

import com.google.inject.AbstractModule;

public class DBDevModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DBDriver.class).to(H2Driver.class);
    }

}