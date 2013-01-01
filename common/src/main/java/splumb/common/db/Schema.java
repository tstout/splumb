package splumb.common.db;

/**
 * Table classes can optionally implement this for schema creation/modification;
 * Note: it is thought initially that create and update need to be different ops.
 * Reality may dictate otherwise.
 */
public interface Schema {
    void create();
    void update();
}
