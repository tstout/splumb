package splumb.common.db;

import org.apache.empire.data.DataType;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;

import java.lang.reflect.Field;

public class TableDef<T> extends DBTable {

    public TableDef(String name, DBDatabase db, T inst) {
        super(name, db);
        //CaseFormat

    }

    private void addColumns(T inst) {
        for(Field field : inst.getClass().getDeclaredFields()) {
            if (DBColumn.class.isAssignableFrom(field.getType())) {

            }
        }
    }


    protected DBColumn charCol(String name, int length) {
        return addColumn(name, DataType.TEXT, length, true);
    }

    protected DBColumn nullableIntCol(String name) {
        return addColumn(name, DataType.INTEGER, 0, false);
    }
}
