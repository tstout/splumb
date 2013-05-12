package splumb.common.db.schema;

import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

class TableDef2 extends DBTable {

    public TableDef2(String name, DBDatabase db) {
        super(name, db);
    }

    DBTableColumn addCol(DBTableColumn col) {
        return addColumn(col.getName(), col.getDataType(), col.getSize(), col.isRequired());
        //addColumn(col);
    }
}
