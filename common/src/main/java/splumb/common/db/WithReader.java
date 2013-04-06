package splumb.common.db;

import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBReader;
import splumb.common.func.Action;

import java.sql.Connection;

public class WithReader {
    public void exec(DBDriver driver, final DBCommand dbCommand, final Action<DBReader> action) {
        new WithConnection().exec(driver, new Action<Connection>() {

            @Override public void invoke(Connection input) {
                DBReader reader = new DBReader();
                reader.open(dbCommand, input);
                try {
                    action.invoke(reader);
                } finally {
                    closeQuietly(reader);
                }
            }
        });
    }

    private void closeQuietly(DBReader reader) {
        try {
            reader.close();
        } catch (Exception ignored) {
        }
    }
}
