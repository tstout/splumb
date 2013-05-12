package splumb.common.db.schema.definition;

import com.google.common.base.Optional;
import splumb.common.db.schema.ColType;

public interface ColumnDef {
    ColType type();
    String name();
    Optional<Integer> length();
}
