package splumb.common.db;

import org.apache.empire.db.DBColumnExpr;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBReader;
import org.apache.empire.db.expr.compare.DBCompareExpr;
import splumb.common.func.Action;

import java.util.List;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.*;

public class Query {
    private DBCommand cmd;
    private DBDriver driver;

    public Query(DBDatabase db, DBDriver driver) {
        this.driver = driver;
        cmd = db.createCommand();
    }

//    public Query withDriver(DBDriver driver) {
//        this.driver = driver;
//        return this;
//    }

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

    public <T> List<T> run(final RowMapper<T> mapper) {
        checkNotNull(driver, "DB driver is required");

        final List<T> result = newArrayList();

        new WithReader().exec(driver, cmd, new Action<DBReader>() {
            @Override
            public void invoke(DBReader input) {
                while (input.moveNext()) {
                    result.add(mapper.mapRow(input));
                }
            }
        });

        return result;
    }
}
