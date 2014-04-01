package splumb.core.db;


import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import db.io.Database;
import db.io.config.ConnectionFactory;
import db.io.config.DBCredentials;
import db.io.migration.Migrators;
import splumb.common.logging.LogPublisher;
import splumb.core.events.HostDbTablesAvailableEvent;

import java.sql.Connection;

public class SplumbDB {
    private final Database db;
    private final DBCredentials credentials;
    private final EventBus eventBus;
    private final LogPublisher logger;

    @Inject
    public SplumbDB(Database db,
                    DBCredentials credentials,
                    EventBus eventBus,
                    LogPublisher logger) {
        this.eventBus = eventBus;
        this.logger = logger;
        this.credentials = credentials;
        this.db = db;
    }

    public DBCredentials credentials() {
        return credentials;
    }

    public Database database() {
        return db;
    }

    // TODO - refactor this away...
    public Connection getConnection() {
        return db.connection(credentials);
    }

    public SplumbDB create() {
        logger.info("Updating internal schema...");
        Migrators
                .liquibase(new ConnectionFactory(credentials, db))
                .update("sql/log-schema.sql");

        eventBus.post(new HostDbTablesAvailableEvent());
        return this;
    }
}
