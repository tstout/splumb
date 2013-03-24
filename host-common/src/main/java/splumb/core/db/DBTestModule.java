package splumb.core.db;

import com.google.inject.AbstractModule;
import joptsimple.OptionParser;
import splumb.common.db.DBDriver;
import splumb.common.db.H2InMemDriver;
import splumb.core.cli.OptValues;


// todo - should this be in a test package?

public class DBTestModule extends AbstractModule {

    @Override
    protected void configure() {

        OptionParser parser = new OptionParser();
        parser.accepts("nodb", "Run without DB");
        //this.install(new CliModule(new String[] {}));

        bind(DBDriver.class).to(H2InMemDriver.class);
        bind(OptValues.class).toInstance(new OptValues() {
            @Override
            public int jmxPort() {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean noDB() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean dropTables() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }
}
