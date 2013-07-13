package splumb.common.db;

import org.apache.empire.db.DBReader;

/**
 * Simple, yet effective RowMapper inspired by Spring's JdbcOperations
 * @param <T>
 */
public interface RowMapper<T> {
    T mapRow(DBReader reader);
}
