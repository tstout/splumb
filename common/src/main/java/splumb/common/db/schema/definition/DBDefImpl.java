package splumb.common.db.schema.definition;

import com.google.common.collect.ImmutableList;
import org.apache.empire.db.DBDatabase;
import splumb.common.db.DBDriver;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

// TODO - refactor namespace(s) such that this is not public

public class DBDefImpl implements DBDef {

    public static final List<TableDef> EMPTY_TABLE_LIST = newArrayList();
    public static final List<FKDef> EMPTY_FK_LIST = newArrayList();

    private final List<TableDef> tables;
    private final List<FKDef> foreignKeys;
    private final DBDatabase db;
    private final DBDriver driver;

    DBDefImpl(List<TableDef> tables, List<FKDef> foreignKeys, String schemaName, DBDriver driver) {
        this.tables = tables;
        this.foreignKeys = foreignKeys;
        this.driver = driver;

        this.db = new DBDatabase(schemaName) {{
            open(DBDefImpl.this.driver.getDriver(),
                 DBDefImpl.this.driver.getConnection());
        }};
    }

    public DBDefImpl(String schemaName, DBDriver driver) {
        this(EMPTY_TABLE_LIST, EMPTY_FK_LIST, schemaName, driver);
    }

    DBDatabase db() {
        return db;
    }

//    void close() {
//        db.close();
//    }

    @Override
    public List<TableDef> tables() {
        return ImmutableList.copyOf(tables);
    }

    @Override
    public List<FKDef> foreignKeys() {
        return ImmutableList.copyOf(foreignKeys);
    }

    @Override public <T> T unwrap(Class<T> clazz) {
        return clazz.cast(db);
    }
}
