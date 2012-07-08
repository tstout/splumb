package splumb.core.db;

import com.google.inject.AbstractModule;
import splumb.common.db.DBDriver;
import splumb.common.db.H2Driver;

public class DBDevModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DBDriver.class).to(H2Driver.class);
    }

}