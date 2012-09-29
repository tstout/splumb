package splumb.plugin.config;

import splumb.common.plugin.AbstractPluginConfig;
import splumb.common.plugin.PluginName;

public class FooServiceConfig extends AbstractPluginConfig {
    @Override
    protected void configure(Builder builder) {
        builder.withBaseScanPackage("sampleplugin");

        builder.withName(PluginName.builder()
                .withName("foo")
                .withOrganization("org")
                .withVersion("1.0")
                .build());
    }
}
