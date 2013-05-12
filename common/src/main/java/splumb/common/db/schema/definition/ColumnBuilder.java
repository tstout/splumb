package splumb.common.db.schema.definition;

import com.google.common.base.Optional;
import org.apache.empire.data.DataMode;
import splumb.common.db.schema.ColType;

import static com.google.common.base.Preconditions.*;

public class ColumnBuilder {
    private String name;
    private Optional<Integer> size = Optional.absent();
    private DataMode dataMode; // TODO - WRAP datamode?
    private ColType dataType;
    private Object defValue;

    public ColumnBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ColumnBuilder ofText() {
        dataType = ColType.VARCHAR;
        return this;
    }

    public ColumnBuilder withDefValue(Object defValue) {
        this.defValue = defValue;
        return this;
    }

    public ColumnBuilder ofDateTime() {
        this.dataType = ColType.DATE_TIME;
        return this;
    }

    public ColumnBuilder ofInt() {
        dataType = ColType.INT;
        return this;
    }

    public ColumnBuilder ofAutoInc() {
        dataType = ColType.AUTOINC;
        return this;
    }

    public ColumnBuilder withSize(int size) {
        this.size = Optional.of(size);
        return this;
    }

    public ColumnBuilder notNull() {
        dataMode = DataMode.NotNull;
        return this;
    }

    public ColumnDef build() {
        checkNotNull(dataType, "Column dataType is required");
        checkNotNull(name, "Column schemaName is required");

        return new ColumnDefImpl(dataType, name, size);

        //return new DBTableColumn(null, dataType, name, size, dataMode, defValue);
    }
}
