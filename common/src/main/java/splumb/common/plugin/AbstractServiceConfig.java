package splumb.common.plugin;


import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

// TODO - is a builder useful here or how about simply
// providing protected methods to configure data.
//

/**
 * Service jars must place this in a package named splumb.plugin.config
 */
public abstract class AbstractServiceConfig implements ServiceConfig {

    private ServiceContext serviceContext;

    public AbstractServiceConfig() {
        Builder builder = new Builder();
        configure(builder);
        serviceContext = builder.build();
    }

    @Override
    public ServiceContext getServiceContext() {
        return serviceContext;
    }

    protected abstract void configure(Builder builder);

    public class Builder {
        private List<String> baseScanPackages = newArrayList();

        public Builder withBaseScanPackage(String baseScanPackage) {
            this.baseScanPackages.add(baseScanPackage);
            return this;
        }

        ServiceContext build() {
            return new ServiceContext(baseScanPackages);
        }
    }
}
