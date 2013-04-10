package splumb.common.db.schema.definition;

import java.util.List;

public interface TableDef {
    List<ColumnDef> columns();
    List<IndexDef> indices();
}
