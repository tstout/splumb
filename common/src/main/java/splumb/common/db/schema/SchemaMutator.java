package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

public interface SchemaMutator {
    SchemaMutator addTables(DBTable... tables);

    SchemaMutator addColumns(DBTable table, DBTableColumn... columns);

    SchemaMutator dropColumns(DBTable table, DBTableColumn... columns);

    SchemaMutator addIndex(DBTable table, DBTableColumn... columns);

    SchemaMutator addFK(DBTableColumn src, DBTableColumn dest);
}
