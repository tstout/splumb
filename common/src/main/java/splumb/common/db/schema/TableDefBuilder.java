package splumb.common.db.schema;


import splumb.common.db.schema.definition.ColumnDef;
import splumb.common.db.schema.definition.DBDef;
import splumb.common.db.schema.definition.IndexDef;
import splumb.common.db.schema.definition.TableDef;

import java.util.List;

import static com.google.common.collect.Lists.*;

public class TableDefBuilder {
    private List<ColumnDef> columns = newArrayList();
    private List<IndexDef> indices = newArrayList();
    private String name;
    private DBDef dbDef;

    public TableDefBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TableDefBuilder addColumn(ColumnDef columnDef) {
        columns.add(columnDef);
        return this;
    }

    public TableDefBuilder addIndex(IndexDef index) {
        indices.add(index);
        return this;
    }

    public TableDefBuilder withDb(DBDef dbDef) {
        this.dbDef = dbDef;
        return this;
    }

    public TableDef build() {
        return new TableDefImpl(columns, indices, dbDef, name);
    }
}
