package splumb.core.db;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import joptsimple.OptionSet;
import org.apache.empire.db.DBCmdType;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBSQLScript;
import org.apache.empire.db.DBTable;
import splumb.common.db.DBDriver;
import splumb.common.db.DataSet;
import splumb.common.logging.Level;
import splumb.core.db.tables.Log;
import splumb.core.db.tables.LogConfig;
import splumb.core.db.tables.LogLevel;
import splumb.core.host.events.DbAvailableEvent;
import splumb.core.host.events.HostDbTablesAvailableEvent;
import splumb.core.logging.HostLogger;

import java.sql.Connection;

import static com.google.common.collect.ImmutableSet.*;

public class SplumbDB extends DBDatabase {

    public final Log Log = new Log(this);
    public final LogLevel LogLevel = new LogLevel(this);
    public final LogConfig LogConfig = new LogConfig(this);

    private DBDriver driver;
    private OptionSet options;
    private EventBus eventBus;

    @Inject
    public SplumbDB(DBDriver driver, OptionSet options, EventBus eventBus) {
        // TODO - need to investigate using schema names.
        //super("SPLUMB");
        this.driver = driver;
        this.options = options;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void init(DbAvailableEvent evt) {
        addRelation(Log.LEVEL.referenceOn(LogLevel.LOG_LEVEL_ID));
        this.open(driver.getDriver(), driver.getConnection());
    }

    public Connection getConnection() {
        return driver.getConnection();
    }

    public void dropAllTables() {

        Connection conn = driver.getConnection();
        DBSQLScript dropScript = new DBSQLScript();

        for (DBTable table : of(Log, LogLevel, LogConfig)) {
            driver.getDriver().getDDLScript(DBCmdType.DROP, table, dropScript);
            dropScript.run(driver.getDriver(), conn, false);
            dropScript.clear();
        }

        commit(conn);
    }

    public SplumbDB create() {

        if (options.has("droptables")) {
            dropAllTables();
        }

        Connection conn = driver.getConnection();

        try {
            //
            // TODO...empire DB must provide a better way...
            //
            DBSQLScript script = new DBSQLScript();
            getCreateDDLScript(driver.getDriver(), script);
            script.run(driver.getDriver(), conn, false);
            addDefaultData(conn);
            commit(conn);
        } catch (Exception e) {
            if (options.has("droptables")) {
                System.out.printf("Exception: %s\n Caused by: %s\n", e.getMessage(), e.getCause().getMessage());
            }
        }

        eventBus.post(new HostDbTablesAvailableEvent());
        return this;
    }

    private void addDefaultData(Connection conn) {

         new DataSet()
                 .withColumns(of(LogLevel.LOG_LEVEL_ID, LogLevel.NAME))
                 .withValues(of(
                         2, "ERROR",
                         1, "INFO",
                         0, "DEBUG"))
                 .insertInto(LogLevel, conn);

         new DataSet()
                 .withColumns(of(LogConfig.LOGGER, LogConfig.LOG_LEVEL))
                 .withValues(of(HostLogger.LOGGER_NAME, Level.DEBUG.ordinal()))
                 .insertInto(LogConfig, driver.getConnection());
    }
}
