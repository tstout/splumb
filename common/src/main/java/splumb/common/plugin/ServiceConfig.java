package splumb.common.plugin;


import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Service jars must place this in a package named splumb.plugin.config
 */
public abstract class ServiceConfig {

    public ServiceConfig() {
        configure(new Builder());
    }

    protected abstract void configure(Builder builder);

    public class Builder {
        private List<String> baseScanPackages = newArrayList();

        public Builder withBaseScanPackage(String baseScanPackage) {
            this.baseScanPackages.add(baseScanPackage);
            return this;
        }

        public ServiceContext build() {
            return new ServiceContext(baseScanPackages);
        }
    }

    public class ServiceContext {
        private final List<String> baseScanPackages;

        ServiceContext(List<String> baseScanPackages) {
            this.baseScanPackages = baseScanPackages;
        }

        public List<String> getBaseScanPackage() {
            return baseScanPackages;
        }
    }

}
