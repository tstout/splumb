package splumb.common.db.schema;

import splumb.common.db.DBDriver;
import splumb.common.db.schema.definition.DBDef;
import splumb.common.db.schema.definition.FKDef;

import java.util.List;

import static com.google.common.collect.Lists.*;

class DBDefBuilder {
    private List<splumb.common.db.schema.definition.TableDef> tables = newArrayList();
    private List<FKDef> foreignKeys = newArrayList();
    private String schema;
    private DBDriver driver;

    DBDefBuilder addTable(splumb.common.db.schema.definition.TableDef tableDef) {
        tables.add(tableDef);
        return this;
    }

    DBDefBuilder addFK(FKDef fkDef) {
        foreignKeys.add(fkDef);
        return this;
    }

    DBDefBuilder withSchemaName(String schema) {
        this.schema = schema;
        return this;
    }

    DBDefBuilder withDriver(DBDriver driver) {
        this.driver = driver;
        return this;
    }

    DBDef build() {
        return new DBDefImpl(tables, foreignKeys, schema, driver);
    }
}
