package splumb.common.db.schema;

class SchemaHistoryRow {
    private final int objectId;
    private final SchemaObjectType.ObjectType objectType;
    private final String objectName;
    private final String parentObjectName;

    private SchemaHistoryRow(int objectId, SchemaObjectType.ObjectType objectType, String objectName,
                             String parentObjectName) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.objectName = objectName;
        this.parentObjectName = parentObjectName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int objectId() {
        return objectId;
    }

    public String parentObjectName() {
        return parentObjectName;
    }

    public SchemaObjectType.ObjectType objectType() {
        return objectType;
    }

    public String objectName() {
        return objectName;
    }

    public static class Builder {
        private int objectId;
        private SchemaObjectType.ObjectType objectType;
        private String objectName;
        private String parentObjectName;

        public Builder withObjectId(int objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder withParentObjectName(String parentObjectName) {
            this.parentObjectName = parentObjectName;
            return this;
        }

        public Builder withObjectType(String objectType) {
            this.objectType = SchemaObjectType.ObjectType.valueOf(objectType);
            return this;
        }

        public Builder withObjectName(String objectName) {
            this.objectName = objectName;
            return this;
        }

        public SchemaHistoryRow build() {
            return new SchemaHistoryRow(objectId, objectType, objectName, parentObjectName);
        }
    }
}
