package splumb.common.message;

public class ROProp<T> {
    public T get() {
        return val;
    }

    public static <T> ROProp<T> newProp(T val) {
        ROProp<T> prop = new ROProp<T>();
        prop.val = val;
        return prop;
    }

    T val;
}
