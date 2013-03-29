package splumb.common.db.schema;

import splumb.common.db.DBDriver;

public class Schemas {
    public static void create(DBDriver driver, SchemaModule... modules) {
        new InternalSchemaCommandBuilder()
                .driver(driver)
                .modules(modules)
                .build();
    }
}
