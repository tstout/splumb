package splumb.common.db.schema;

import java.sql.Connection;

public interface HasDefaults {
    void setDefaults(Connection connection);
}
