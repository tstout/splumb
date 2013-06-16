package splumb.plugin.config;

import splumb.common.plugin.AbstractPluginConfig;
import splumb.common.plugin.PluginName;

public class MessagingServiceConfig extends AbstractPluginConfig {
    @Override
    protected void configure(PluginBuilder builder) {
        builder.withBaseScanPackage("splumb.messaging")
                .withName(PluginName.builder()
                        .withName("messaging")
                        .withOrganization("splumb")
                        .withVersion("1.0")
                        .build());
    }
}
