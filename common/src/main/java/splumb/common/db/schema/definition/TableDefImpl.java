package splumb.common.db.schema.definition;

import com.google.common.collect.ImmutableList;

import java.util.List;

class TableDefImpl implements TableDef {
    private final List<ColumnDef> columns;
    private final List<IndexDef> indices;

    TableDefImpl(List<ColumnDef> columns, List<IndexDef> indices) {
        this.columns = columns;
        this.indices = indices;
    }

    @Override
    public List<ColumnDef> columns() {
        return ImmutableList.copyOf(columns);
    }

    @Override
    public List<IndexDef> indices() {
        return ImmutableList.copyOf(indices);
    }


}
