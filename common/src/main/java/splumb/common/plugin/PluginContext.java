package splumb.common.plugin;

import java.util.List;

public class PluginContext {
    private final List<String> baseScanPackages;
    private final PluginName name;

    PluginContext(List<String> baseScanPackages, PluginName name) {
        this.baseScanPackages = baseScanPackages;
        this.name = name;
    }

    public List<String> basePackages() {
        return baseScanPackages;
    }

    public PluginName name() {
        return name;
    }
}
