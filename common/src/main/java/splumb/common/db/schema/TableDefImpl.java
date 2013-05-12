package splumb.common.db.schema;

import com.google.common.collect.ImmutableList;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;
import splumb.common.db.schema.definition.ColumnDef;
import splumb.common.db.schema.definition.DBDef;
import splumb.common.db.schema.definition.IndexDef;
import splumb.common.db.schema.definition.TableDef;

import java.util.List;

class TableDefImpl implements TableDef {
    private final List<ColumnDef> columns;
    private final List<IndexDef> indices;
    private final String name;
    private final DBDef dbDef;

    TableDefImpl(List<ColumnDef> columns, List<IndexDef> indices, DBDef dbDef, String name) {
        this.columns = columns;
        this.indices = indices;
        this.name = name;
        this.dbDef = dbDef;
    }

    @Override
    public List<ColumnDef> columns() {
        return ImmutableList.copyOf(columns);
    }

    @Override
    public List<IndexDef> indices() {
        return ImmutableList.copyOf(indices);
    }

    @Override public <T> T unwrap(Class<T> clazz) {
        return clazz.cast(new DBTable(name, dbDef.unwrap(DBDatabase.class)));
    }
}
