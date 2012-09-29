package splumb.common.plugin;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.*;

public final class PluginName {

    private final String organization;
    private final String version;
    private final String name;

    private PluginName(String organization, String version, String name) {

        this.organization = organization;
        this.version = version;
        this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final PluginName other = (PluginName) obj;

        return obj instanceof PluginName &&
                equal(getOrganization(), other.getOrganization()) &&
                equal(getVersion(), other.getVersion()) &&
                equal(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOrganization(), getVersion(), getName());
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getOrganization() {
        return organization;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String organization;
        private String version;
        private String name;

        public Builder withOrganization(String organization) {
            this.organization = organization;
            return this;
        }

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public PluginName build() {
            return new PluginName(organization, version, name);
        }
    }
}
