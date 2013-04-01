package splumb.common.db;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.schema.ColumnBuilder;

public final class Columns {

    private Columns() {}

    // TODO - consider mod such that these methods only create a Column object and do not add them to
    // the DB. This will fit better with the fledgeling schema versioning...well, it looks like this
    // might not be allowed by the empire api.
    //
    public static DBTableColumn nullableCharCol(DBTable table, String name, int length) {

        return table.addColumn(name, DataType.TEXT, length, false);
    }

    public static DBTableColumn varchar(DBTable table, String name, int length) {
        return table.addColumn(name, DataType.TEXT, length, true);
    }

    public static DBTableColumn varchar(String name, int length) {
        return col()
                .ofText()
                .withName(name)
                .withSize(length)
                .build();
    }

    public static ColumnBuilder col() {
        return new ColumnBuilder();
    }

//    public static DBTableColumn varchar(String name, int length) {
//        DataMode dm = (required ? DataMode.NotNull : DataMode.Nullable);
//        return new DBTableColumn(this, type, columnName, size, dm, defValue);
//
//
//        return new DBTableColumn(null, name, DataType.TEXT, length, DataMode.Nullable, null);
//    }

    public static DBTableColumn nullableIntCol(DBTable table, String name) {
        return table.addColumn(name, DataType.INTEGER, 0, false);
    }

    public static DBTableColumn autoInc(DBTable table, String name) {
        return table.addColumn(name, DataType.AUTOINC, 0, true);
    }

    public static DBTableColumn autoInc(String name) {
        return col()
                .ofAutoInc()
                .withName(name)
                .build();
    }

    public static DBTableColumn intCol(DBTable table, String name) {
        return table.addColumn(name, DataType.INTEGER, 0, true);
    }

    public static DBTableColumn intCol(String name) {
        return col()
                .ofInt()
                .withName(name)
                .build();
    }

    public static DBTableColumn dateTime(DBTable table, String name) {
        return table.addColumn(name, DataType.DATETIME, 0, true);
    }

    public static DBTableColumn dateTime(String name) {
        return col()
                .ofDateTime()
                .withName(name)
                .build();
    }
}
