package splumb.core.host;

import com.google.inject.AbstractModule;
import joptsimple.OptionSet;

class HostInjectModule extends AbstractModule {

    private String[] args;
    private OptionSet optionSet;

    public HostInjectModule(OptionSet optionSet) {
        this.optionSet = optionSet;
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