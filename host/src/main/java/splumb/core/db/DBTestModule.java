package splumb.core.db;

import com.google.inject.AbstractModule;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;


// todo - should this be in a test package?

public class DBTestModule extends AbstractModule {

    private OptionSet optionSet = new OptionParser().parse(new String[] {});

    @Override
    protected void configure() {
        bind(DBDriver.class).to(H2InMemDriver.class);
        bind(OptionSet.class).toInstance(optionSet);
    }
}
