package splumb.core.host;

import com.google.inject.AbstractModule;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

class HostInjectModule extends AbstractModule {

    private String[] args;
    private OptionSet optionSet;

    public HostInjectModule(String[] args) {
        OptionParser parser = new OptionParser();
        parser.accepts("nodb", "Run without DB");
        parser.accepts("droptables", "drop and recreate tables at startup");

        optionSet = parser.parse(args);
    }

    @Override
    protected void configure() {
        bind(OptionSet.class).toInstance(optionSet);

//        bindListener(Matchers.any(), new TypeListener() {
//            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
//                typeEncounter.register(new InjectionListener<I>() {
//                    public void afterInjection(I i) {
//
//                        //eventBus.register(i);
//                    }
//                });
//            }
//        });


    }
}