package splumb.common.db;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public final class Columns {

    private Columns() {}

    public static DBTableColumn nullableCharCol(DBTable table, String name, int length) {
        return table.addColumn(name, DataType.TEXT, length, false);
    }

    public static DBTableColumn varchar(DBTable table, String name, int length) {
        return table.addColumn(name, DataType.TEXT, length, true);
    }

    public static DBTableColumn nullableIntCol(DBTable table, String name) {
        return table.addColumn(name, DataType.INTEGER, 0, false);
    }

    public static DBTableColumn autoInc(DBTable table, String name) {
        return table.addColumn(name, DataType.AUTOINC, 0, true);
    }

    public static DBTableColumn intCol(DBTable table, String name) {
        return table.addColumn(name, DataType.INTEGER, 0, true);
    }

    public static DBTableColumn dateTime(DBTable table, String name) {
        return table.addColumn(name, DataType.DATETIME, 0, true);
    }

    public static class Builder {
        public DBColumn build() {
            return null;
        }

    }
}
