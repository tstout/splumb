package splumb.common.db;

import org.apache.empire.db.DBDatabaseDriver;
import org.apache.empire.db.h2.DBDatabaseDriverH2;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class H2InMemDriver implements DBDriver {

    String url = "jdbc:h2:mem:splumb";
    private JdbcConnectionPool pool = JdbcConnectionPool.create(url, "sa", "");
    private DBDatabaseDriverH2 driver = new DBDatabaseDriverH2();

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



}