package splumb.common.db.schema;

import org.apache.empire.data.DataMode;
import org.apache.empire.data.DataType;
import org.apache.empire.db.DBTableColumn;

import static com.google.common.base.Preconditions.checkNotNull;

public class ColumnBuilder {
    private String name;
    private int size;
    private DataMode dataMode;
    private DataType dataType;
    private Object defValue;

    public ColumnBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ColumnBuilder ofText() {
        dataType = DataType.TEXT;
        return this;
    }

    public ColumnBuilder withDefValue(Object defValue) {
        this.defValue = defValue;
        return this;
    }

    public ColumnBuilder ofDateTime() {
        this.dataType = DataType.DATETIME;
        return this;
    }

    public ColumnBuilder ofInt() {
        dataType = DataType.INTEGER;
        return this;
    }

    public ColumnBuilder ofAutoInc() {
        dataType = DataType.AUTOINC;
        return this;
    }

    public ColumnBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public ColumnBuilder notNull() {
        dataMode = DataMode.NotNull;
        return this;
    }

    public DBTableColumn build() {
        checkNotNull(dataType, "Column dataType is required");
        checkNotNull(name, "Column schemaName is required");
        return new DBTableColumn(null, dataType, name, size, dataMode, defValue);
    }
}
