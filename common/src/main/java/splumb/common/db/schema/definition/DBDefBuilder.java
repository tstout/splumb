package splumb.common.db.schema.definition;

import java.util.List;

import static com.google.common.collect.Lists.*;

class DBDefBuilder {
    private List<TableDef> tables = newArrayList();
    private List<FKDef> foreignKeys = newArrayList();

    DBDefBuilder addTable(TableDef tableDef) {
        tables.add(tableDef);
        return this;
    }

    DBDefBuilder addFK(FKDef fkDef) {
        foreignKeys.add(fkDef);
        return this;
    }

    DBDef build() {
        return new DBDefImpl(tables, foreignKeys);
    }
}
