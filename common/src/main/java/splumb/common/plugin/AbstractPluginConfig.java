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
        Builder builder = new Builder();
        configure(builder);
        serviceContext = builder.build();
    }

    @Override
    public PluginContext getServiceContext() {
        return serviceContext;
    }

    protected abstract void configure(Builder builder);

    public class Builder {
        private List<String> baseScanPackages = newArrayList();
        private PluginName name;

        public Builder withBaseScanPackage(String baseScanPackage) {
            this.baseScanPackages.add(baseScanPackage);
            return this;
        }

        public Builder withName(PluginName name) {
            this.name = name;
            return this;
        }

        PluginContext build() {
            return new PluginContext(baseScanPackages, name);
        }
    }
}
