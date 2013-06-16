package splumb.common.plugin;


import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

// TODO - is a builder useful here or how about simply
// providing protected methods to configure data.
//

/**
 * Plugin jars must place this in a package named splumb.plugin.config
 */
public abstract class AbstractPluginConfig implements PluginConfig {

    private PluginContext serviceContext;

    public AbstractPluginConfig() {
        PluginBuilder builder = new PluginBuilder();
        configure(builder);
        serviceContext = builder.build();
    }

    @Override
    public PluginContext getServiceContext() {
        return serviceContext;
    }

    protected abstract void configure(PluginBuilder builder);

    public class PluginBuilder {
        private List<String> baseScanPackages = newArrayList();
        private PluginName name;

        // TODO - replace with Package object instead of string....

        /**
         * Specify the base package containing plugins to be loaded.
         * Plugins are identified as any class implementing
         * com.google.common.util.concurrent.Service
         * @param baseScanPackage
         * @return
         */
        public PluginBuilder withBaseScanPackage(String baseScanPackage) {
            this.baseScanPackages.add(baseScanPackage);
            return this;
        }

        public PluginBuilder withName(PluginName name) {
            this.name = name;
            return this;
        }

        PluginContext build() {
            return new PluginContext(baseScanPackages, name);
        }
    }
}
