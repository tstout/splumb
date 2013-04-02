package splumb.common.db.schema;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class SchemaVersion {
    private final int major;
    private final int minor;
    private final int point;
    private final String name;

    private SchemaVersion(int major, int minor, int point, String name) {
        this.major = major;
        this.minor = minor;
        this.point = point;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String schemaName() {
        return name;
    }

    public int major() {
        return major;
    }

    public int minor() {
        return minor;
    }

    public int point() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchemaVersion other = (SchemaVersion) o;
        return Objects.equal(major, other.major) &&
                Objects.equal(minor, other.minor) &&
                Objects.equal(point, other.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(major, minor, point);
    }

    public static class Builder {
        private int major;
        private int minor;
        private int point;
        private String name;

        public Builder withMajor(int major) {
            this.major = major;
            return this;
        }

        public Builder withSchemaName(String name) {
            this.name = name;
            return this;
        }

        public Builder withMinor(int minor) {
            this.minor = minor;
            return this;
        }

        public Builder withPoint(int point) {
            this.point = point;
            return this;
        }

        public SchemaVersion build() {
            checkNotNull(name, "Schema schemaName is required");
            return new SchemaVersion(major, minor, point, name);
        }
    }
}
