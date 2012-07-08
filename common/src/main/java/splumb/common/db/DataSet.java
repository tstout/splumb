package splumb.common.db;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.empire.db.DBRecord;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

import java.sql.Connection;

import static com.google.common.base.Preconditions.checkState;

/**
 * The gist of this class is to ease coding multiple-row inserts with Empire DB.
 */
public class DataSet {
    private ImmutableSet<DBTableColumn> columns;
    private ImmutableSet values;
    private DBDriver driver;

    /**
     * Specify what columns to insert
     * @param columns
     * @return
     */
    public DataSet withColumns(ImmutableSet<DBTableColumn> columns) {
        this.columns = columns;
        return this;
    }

    /**
     * Specify the set of values to insert.  It is assumed that columns.size() % values.size() is zero.
     * @param values
     * @return
     */
    public DataSet withValues(ImmutableSet values) {
        this.values = values;
        return this;
    }

    public void insertInto(DBTable table, Connection conn) {
        checkState((values.size() % columns.size()) == 0,
                "Number of columns does not correlate with number of values");

        // TODO - minor nitpick...see if you can change this to not require
        // converting the Immutable sets to lists and arrays.  I bet guava
        // can handle this. It simply was not immediately obvious how to do this.
        //
        Object[] valueArray = values.toArray();
        ImmutableList<DBTableColumn> columnList = columns.asList();

        for (int i = 0; i <  values.size(); i += columns.size()) {

            DBRecord record = new DBRecord();
            record.create(table, conn);

            for (int j = 0; j < columnList.size(); j++) {
                record.setValue(columnList.get(j), valueArray[i + j]);
            }

            record.update(conn);
        }
    }
}
