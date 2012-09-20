package splumb.common.plugin;

import java.util.List;

public class ServiceContext {
    private final List<String> baseScanPackages;

    ServiceContext(List<String> baseScanPackages) {
        this.baseScanPackages = baseScanPackages;
    }

    public List<String> getBaseScanPackage() {
        return baseScanPackages;
    }
}
