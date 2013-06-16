package splumb.plugin.config;

import splumb.common.plugin.AbstractPluginConfig;
import splumb.common.plugin.PluginName;

public class SpmonServiceConfig extends AbstractPluginConfig {
    @Override protected void configure(PluginBuilder builder) {
        builder.withBaseScanPackage("splumb.monitor")
                .withName(PluginName.builder()
                        .withName("spmon")
                        .withOrganization("splumb")
                        .withVersion("0.1")
                        .build());

    }
}
