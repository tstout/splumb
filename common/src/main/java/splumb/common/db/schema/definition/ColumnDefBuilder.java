package splumb.common.db.schema.definition;

import com.google.common.base.Optional;
import splumb.common.db.schema.ColType;

class ColumnDefBuilder {
    private ColType type;
    private String name;
    private Optional<Integer> length = Optional.absent();

    ColumnDefBuilder withType(ColType type) {
        this.type = type;
        return this;
    }

    ColumnDefBuilder withName(String name) {
        this.name = name;
        return this;
    }

    ColumnDefBuilder withLength(Integer length ){
        this.length = Optional.fromNullable(length);
        return this;
    }

    ColumnDef build() {
        return new ColumnDefImpl(type, name, length);
    }
}