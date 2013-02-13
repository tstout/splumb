package splumb.core.db;

import com.google.inject.AbstractModule;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;


// todo - should this be in a test package?

public class DBTestModule extends AbstractModule {

    private OptionSet optionSet;

    @Override
    protected void configure() {

        OptionParser parser = new OptionParser();
        parser.accepts("nodb", "Run without DB");
        //optionSet = parser.parse(new String[] {"-nodb"});
        optionSet = parser.parse(new String[] {});

        bind(DBDriver.class).to(H2InMemDriver.class);
        bind(OptionSet.class).toInstance(optionSet);
    }
}
