package splumb.common.db.schema;

import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;
import splumb.common.db.schema.definition.ColumnDef;
import splumb.common.db.schema.definition.TableDef;

import static splumb.common.db.Columns.*;

public enum ColType {
    /**
     *
     */
    INT(new IntConverter()),
    /**
     *
     */
    VARCHAR(new CharConverter()),
    /**
     *
     */
    AUTOINC(new AutoIncConverter()),
    /**
     *
     */
    DATE_TIME(new DateTimeConverter());

    private Converter converter;

    public <T> T convert(TableDef tableDef, ColumnDef colDef, Class<T> clazz) {
        return clazz.cast(converter.convert(tableDef, colDef));
    }

    private ColType(Converter converter) {
        this.converter = converter;
    }

    interface Converter {
        DBTableColumn convert(TableDef tableDef, ColumnDef colDef);
    }

    static class IntConverter implements Converter {

        @Override public DBTableColumn convert(TableDef tableDef, ColumnDef colDef) {
            return intCol(tableDef.unwrap(DBTable.class), colDef.name());
        }
    }

    static class CharConverter implements Converter {

        @Override public DBTableColumn convert(TableDef tableDef, ColumnDef colDef) {
            return varchar(tableDef.unwrap(DBTable.class), colDef.name(), colDef.length().or(0));
        }
    }

    static class AutoIncConverter implements Converter {

        @Override public DBTableColumn convert(TableDef tableDef, ColumnDef colDef) {
            return autoInc(tableDef.unwrap(DBTable.class), colDef.name());
        }
    }

    static class DateTimeConverter implements Converter {

        @Override public DBTableColumn convert(TableDef tableDef, ColumnDef colDef) {
            return dateTime(tableDef.unwrap(DBTable.class), colDef.name());
        }
    }
}
