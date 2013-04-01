package splumb.common.db.schema;

import org.apache.empire.db.DBReader;
import splumb.common.db.DBDriver;
import splumb.common.db.Query;
import splumb.common.db.RowMapper;

import java.util.List;

class SchemaHistory {
    private DBDriver driver;
    private SchemaMgmtDb db;
    private SchemaVersion version;

    SchemaHistory(DBDriver driver, SchemaVersion version) {
        this.driver = driver;
        this.db = new SchemaMgmtDb();
        //db.open(driver.getDriver(), driver.getConnection());
    }

    List<SchemaHistoryRow> forTable(String tableName) {
        return new Query(db)
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
