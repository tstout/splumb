package splumb.core.db;

import org.apache.empire.db.DBDatabaseDriver;

import java.sql.Connection;

public interface DBDriverFactory {
    DBDatabaseDriver getDriver();

    Connection getConnection();
}
