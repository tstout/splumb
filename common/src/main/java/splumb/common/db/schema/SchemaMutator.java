package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

interface SchemaMutator {
    SchemaMutator addTables(String... tableNames);

    SchemaMutator addColumns(String tableName, DBTableColumn... columns);

    SchemaMutator dropColumns(DBTable table, DBTableColumn... columns);

    SchemaMutator addIndex(DBTable table, DBTableColumn... columns);

    SchemaMutator addFK(DBTableColumn src, DBTableColumn dest);
}
