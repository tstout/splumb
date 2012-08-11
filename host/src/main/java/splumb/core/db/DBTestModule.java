package splumb.core.db;

import com.google.inject.AbstractModule;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;


// todo - should this be in a test package?

public class DBTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBDriver.class).to(H2InMemDriver.class);
    }
}
