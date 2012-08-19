package splumb.common.db;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBTable;

public class Columns {

    public static DBColumn nullableCharCol(DBTable table, String name, int length) {
        return table.addColumn(name, DataType.TEXT, length, false);
    }

    public static DBColumn charCol(DBTable table, String name, int length) {
        return table.addColumn(name, DataType.TEXT, length, true);
    }

    public static DBColumn nullableIntCol(DBTable table, String name) {
        return table.addColumn(name, DataType.INTEGER, 0, false);
    }

    public static DBColumn intCol(DBTable table, String name) {
        return table.addColumn(name, DataType.INTEGER, 0, true);
    }

    public static class Builder {
        public DBColumn build() {
            return null;
        }

    }
}
