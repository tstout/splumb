package splumb.common.db.schema;

import org.apache.empire.db.DBReader;
import splumb.common.db.DBDriver;
import splumb.common.db.Query;
import splumb.common.db.RowMapper;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

class SchemaHistory {
    private DBDriver driver;
    private SchemaMgmtDb db;

    SchemaHistory(DBDriver driver, SchemaMgmtDb db) {
        this.driver = driver;
        this.db = db;
    }

    public boolean tableExists(String tableName, String schema) {
        return new TableListBuilder()
                .driver(driver)
                .schemaPattern(schema)
                .tablePattern(tableName)
                .build()
                .size() != 0;
    }

    List<SchemaHistoryRow> forTable(String tableName, SchemaVersion version) {
        if (!tableExists(tableName, version.schemaName())) {
            return newArrayList();
        }

        return new Query(db, driver)
                .select(db.SCHEMA_OBJECT.OBJECT_ID,
                        db.SCHEMA_OBJECT.OBJECT_NAME,
                        db.SCHEMA_OBJECT.OBJECT_TYPE,
                        db.SCHEMA_OBJECT.PARENT_OBJECT_NAME)
                .where(db.SCHEMA_OBJECT.VERSION_MAJOR.is(version.major())
                    .and(db.SCHEMA_OBJECT.VERSION_MINOR.is(version.minor())
                    .and(db.SCHEMA_OBJECT.VERSION_POINT.is(version.point())
                    .and(db.SCHEMA_OBJECT.PARENT_OBJECT_NAME.is(tableName)))))
                .run(new SchemaHistoryRowMapper());
    }

    class SchemaHistoryRowMapper implements RowMapper<SchemaHistoryRow> {
        @Override
        public SchemaHistoryRow mapRow(DBReader reader) {
            return SchemaHistoryRow.builder()
                    .withObjectId(reader.getInt(db.SCHEMA_OBJECT.OBJECT_ID))
                    .withObjectName(reader.getString(db.SCHEMA_OBJECT.OBJECT_NAME))
                    .withObjectType(reader.getString(db.SCHEMA_OBJECT.OBJECT_TYPE))
                    .withParentObjectName(reader.getString(db.SCHEMA_OBJECT.PARENT_OBJECT_NAME))
                    .build();
        }
    }
}
