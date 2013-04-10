package splumb.common.db.schema.definition;

import java.util.List;

public interface IndexDef {
    String name();
    List<ColumnDef> columns();
}
