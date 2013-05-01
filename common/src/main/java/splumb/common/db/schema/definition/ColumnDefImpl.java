package splumb.common.db.schema.definition;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import org.apache.empire.db.DBColumn;

class ColumnDefImpl implements ColumnDef {

    private final ColType type;
    private final String name;
    private final Optional<Integer> length;
    //private DBColumn nativeCol;

    ColumnDefImpl(ColType type, String name, Optional<Integer> length) {
        this.type = type;
        this.name = name;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ColumnDefImpl other = (ColumnDefImpl) o;
        return Objects.equal(type, other.type) &&
                Objects.equal(name, other.length) &&
                Objects.equal(length, other.length);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, name, length);
    }

    @Override
    public ColType type() {
        return type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override public Optional<Integer> length() {
        return length;
    }

    enum InternalColType {

    }

    DBColumn nativeColumn() {
        //Columns.col().
        return null;
    }
}
