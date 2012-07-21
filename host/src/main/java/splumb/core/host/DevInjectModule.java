package splumb.core.host;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

class DevInjectModule extends AbstractModule {

    private String[] args;
    private OptionSet optionSet;

    public DevInjectModule(String[] args) {
        OptionParser parser = new OptionParser();
            parser.accepts("nodb", "Run without DB");

            optionSet = parser.parse(args);
    }

    @Override
    protected void configure() {
        bind(OptionSet.class).toInstance(optionSet);
    }
}