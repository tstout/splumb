package splumb.common.util;

public class Prop<T> {
    public T get() {
        return val;
    }

    public void set(T val) {
        this.val = val;
    }

    public static <T> Prop<T> newProp() {
        return new Prop<T>();
    }

    public static <T> Prop<T> newProp(T initVal) {
        Prop<T> prop = newProp();
        prop.set(initVal);
        return prop;
    }

    T val;
}
