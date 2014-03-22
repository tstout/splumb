package splumb.core.cli;

import com.google.inject.AbstractModule;

public class CliModule extends AbstractModule {
    private String[] args = new String[]{};
    //private Options options;

    public CliModule(String[] args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        Values.Builder builder = Values.builder();

        // TODO - this mapping could be moved into the Args enum....
        new Options()
                .register(Options.Args.HELP, new HelpAction())
                .register(Options.Args.NO_DB, new NoDbAction())
                .process(args, builder);

        bind(OptValues.class).toInstance(builder.build());
    }

}
