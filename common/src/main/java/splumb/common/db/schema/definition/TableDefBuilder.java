package splumb.common.db.schema.definition;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class TableDefBuilder {
    private List<ColumnDef> columns = newArrayList();
    private List<IndexDef> indices = newArrayList();

    TableDefBuilder addColumn(ColumnDef columnDef) {
        columns.add(columnDef);
        return this;
    }

    TableDefBuilder addIndex(IndexDef index) {
        indices.add(index);
        return this;
    }

    TableDef build() {
        return new TableDefImpl(columns, indices);
    }
}
