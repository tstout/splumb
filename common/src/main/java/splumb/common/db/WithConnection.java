package splumb.common.db;

import splumb.common.func.Action;

import java.sql.Connection;

public class WithConnection {
    // TODO - inject a logger here?

    public void exec(DBDriver driver, Action<Connection> action) {
        Connection conn = driver.getConnection();
        try {
            action.invoke(conn);
        } finally {
            closeQuietly(conn);
        }
    }

    private void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception ignored) {
        }
    }
}
