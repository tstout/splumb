package splumb.common.db.schema.definition;

import com.google.common.collect.ImmutableList;

import java.util.List;

class DBDefImpl implements DBDef {
    private final List<TableDef> tables;
    private final List<FKDef> foreignKeys;

    DBDefImpl(List<TableDef> tables, List<FKDef> foreignKeys) {
        this.tables = tables;
        this.foreignKeys = foreignKeys;
    }

    @Override
    public List<TableDef> tables() {
        return ImmutableList.copyOf(tables);
    }

    @Override
    public List<FKDef> foreignKeys() {
        return ImmutableList.copyOf(foreignKeys);
    }
}
