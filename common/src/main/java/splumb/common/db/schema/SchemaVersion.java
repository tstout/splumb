package splumb.common.db.schema;

public class SchemaVersion {
    private final int major;
    private final int minor;
    private final int point;

    private SchemaVersion(int major, int minor, int point) {
        this.major = major;
        this.minor = minor;
        this.point = point;
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private int major;
        private int minor;
        private int point;

        public Builder withMajor(int major) {
            this.major = major;
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
            return new SchemaVersion(major, minor, point);
        }
    }
}
