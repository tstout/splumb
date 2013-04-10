package splumb.common.db.schema.definition;

public interface FKDef {
    TableDef srcTable();
    TableDef refTable();
    ColumnDef srcColumn();
    ColumnDef destColumn();
}
