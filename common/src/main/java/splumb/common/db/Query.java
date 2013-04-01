package splumb.common.db;

import org.apache.empire.db.DBColumnExpr;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBReader;
import org.apache.empire.db.expr.compare.DBCompareExpr;

import java.util.List;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.newArrayList;

public class Query {
    private DBCommand cmd;
    private DBDatabase db;
    private DBDriver driver;

    public Query(DBDatabase db) {
        this.db = db;
        cmd = db.createCommand();
    }

    public Query withDriver(DBDriver driver) {
        this.driver = driver;
        return this;
    }

    public Query select(DBColumnExpr... cols) {
        cmd.select(cols);
        return this;
    }

    public Query orderBy(DBColumnExpr... cols) {
        cmd.orderBy(cols);
        return this;
    }

    public Query where(DBCompareExpr expr) {
        cmd.where(expr);
        return this;
    }

    public <T> List<T> run(RowMapper<T> mapper) {
        checkNotNull(driver, "DB driver is required");

        DBReader reader = new DBReader();
        reader.open(cmd, driver.getConnection());

        List<T> result = newArrayList();
        while (reader.moveNext()) {
            result.add(mapper.mapRow(reader));
        }

        return result;
    }
}
