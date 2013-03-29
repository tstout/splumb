package splumb.common.db.schema;

public interface SchemaModule {
    SchemaVersion version();
    void configure(SchemaMutator mutator);
}
