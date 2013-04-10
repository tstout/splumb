package splumb.common.db.schema.definition;

import com.google.common.base.Optional;

public interface ColumnDef {
    ColType type();
    String name();
    Optional<Integer> length();
}
