package splumb.common.db.schema;

import java.util.List;

public interface SchemaMetaData {
    List<String> listTables(String schema);

    List<String> listColumns(String schema, String tableName);
}
