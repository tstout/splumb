package splumb.plugin.config;

import splumb.common.plugin.AbstractPluginConfig;
import splumb.common.plugin.PluginName;

public class CronServiceConfig extends AbstractPluginConfig {

    @Override
    protected void configure(Builder builder) {
        builder.withBaseScanPackage("splumb.cron")
                .withName(PluginName.builder()
                        .withName("cron")
                        .withOrganization("splumb")
                        .withVersion("1.0")
                        .build());
    }
}
