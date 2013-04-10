package splumb.common.db.schema.definition;

import java.util.List;

public interface DBDef {
    List<TableDef> tables();
    List<FKDef> foreignKeys();
}
