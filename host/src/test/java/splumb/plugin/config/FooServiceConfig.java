package splumb.plugin.config;

import splumb.common.plugin.AbstractServiceConfig;

public class FooServiceConfig extends AbstractServiceConfig {
    @Override
    protected void configure(Builder builder) {
        builder.withBaseScanPackage("sampleplugin");
    }
}
