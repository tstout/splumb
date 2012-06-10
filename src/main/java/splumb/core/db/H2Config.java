package splumb.core.db;

import org.apache.empire.db.DBDatabaseDriver;
import org.apache.empire.db.h2.DBDatabaseDriverH2;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class H2Config {

    String url = "jdbc:h2:tcp://127.0.0.1/splumb";
    private JdbcConnectionPool pool = JdbcConnectionPool.create(url, "sa", "");
    private DBDatabaseDriverH2 driver;

    public H2Config() {
        driver = new DBDatabaseDriverH2();
    }

    public DBDatabaseDriver getDriver() {
        return driver;
    }

    public Connection getConnection() {
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
