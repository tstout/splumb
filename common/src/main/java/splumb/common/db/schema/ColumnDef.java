package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public interface ColumnDef {
    DBTableColumn def(DBTable table);
}
