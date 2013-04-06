package splumb.common.db;

import org.apache.empire.db.DBDatabaseDriver;
import org.apache.empire.db.h2.DBDatabaseDriverH2;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

import static com.google.common.base.Throwables.propagate;

public class H2Driver implements DBDriver {

    private String url = "jdbc:h2:tcp://127.0.0.1/~/.splumb/db/splumb;INIT=CREATE SCHEMA IF NOT EXISTS SPLUMB";
    //private String url = "jdbc:h2:tcp://127.0.0.1/~/.splumb/db/splumb";
    private JdbcConnectionPool pool = JdbcConnectionPool.create(url, "sa", "");
    private DBDatabaseDriverH2 driver = new DBDatabaseDriverH2();

    public H2Driver() {
        pool.setMaxConnections(5);
    }

    @Override
    public DBDatabaseDriver getDriver() {
        return driver;
    }

    @Override
    public Connection getConnection() {
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void shutdown() {
        try {
            driver.executeSQL("shutdown", null, pool.getConnection(), null);
        } catch (SQLException e) {
            throw propagate(e);
        }
    }
}
