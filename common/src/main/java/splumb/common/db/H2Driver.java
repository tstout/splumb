package splumb.common.db;

import org.apache.empire.db.DBDatabaseDriver;
import org.apache.empire.db.h2.DBDatabaseDriverH2;
import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class H2Driver implements DBDriver {

    String url = "jdbc:h2:tcp://127.0.0.1/splumb";
    private JdbcConnectionPool pool = JdbcConnectionPool.create(url, "sa", "");
    private DBDatabaseDriverH2 driver = new DBDatabaseDriverH2();

    public H2Driver() {
        pool.setMaxConnections(5);
        //System.out.printf("%s\n", getClass().getProtectionDomain().getCodeSource().getLocation().toString());
    }


    @Override
    public DBDatabaseDriver getDriver() {
        return driver;
    }

    @Override
    public Connection getConnection() {

        int live = pool.getActiveConnections();

        try {
            return pool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
