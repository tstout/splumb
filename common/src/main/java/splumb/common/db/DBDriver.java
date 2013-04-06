package splumb.common.db;

import org.apache.empire.db.DBDatabaseDriver;

import java.sql.Connection;

public interface DBDriver {
    DBDatabaseDriver getDriver();

    Connection getConnection();

    void shutdown();
}
