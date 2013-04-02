package splumb.common.db.schema;

import com.google.common.base.Optional;
import splumb.common.db.DBDriver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.google.common.base.Optional.*;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Lists.*;

public class TableListBuilder {
    private Optional<String> schema = absent();
    private Optional<String> table = absent();
    private Optional<String> catalog = absent();
    private DBDriver driver;

    public TableListBuilder schemaPattern(String schema) {
        this.schema = fromNullable(schema);
        return this;
    }

    public TableListBuilder driver(DBDriver driver) {
        this.driver = driver;
        return this;
    }

    public TableListBuilder tablePattern(String table) {
        this.table = fromNullable(table);
        return this;
    }

    public TableListBuilder catalog(String catalog) {
        this.catalog = fromNullable(catalog);
        return this;
    }

    public List<String> build() {
        checkNotNull(driver, "DB Driver is required");

        List<String> tables = newArrayList();
        Connection conn = driver.getConnection();

        try {
            ResultSet rs = conn
                    .getMetaData()
                    .getTables(catalog.orNull(),
                            schema.orNull(),
                            table.orNull(),
                            new String[]{"TABLE"});

            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            throw propagate(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                propagate(e);
            }
        }
        return tables;
    }


}
