package splumb.common.message;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class PropFactory {
    private Map<Method, Object> backingFields = new HashMap<Method, Object>();

    @SuppressWarnings("unchecked")
    public <T> T newProp(Class<?> intf) {
        //
        // TODO need to research how to determine the best
        // classloader to use here. I'm leaning toward
        // the null classloader. In addition,
        // This factory needs to limit what return types
        // can be exposed in the methods. For the moment
        // it needs to be restricted to Prop<T>.
        //
        //Object c = T.class;

        return (T) Proxy.newProxyInstance(Thread.currentThread()
                .getContextClassLoader(),
                // intf.getClass().getClassLoader(),
                new Class<?>[]{intf}, new Handler(intf));
    }


    //
// TODO - exp with method that creates a chain interceptor for get/set..
//
    class Handler implements InvocationHandler {
        public Handler(Class<?> intf) {
            for (Method m : intf.getMethods()) {
                //
                // TODO - might need to handle methods that do not
                // return a typeof Prop<T> to simply be pass-through...
                //
                try {
                    backingFields.put(m,
                            m.getReturnType()
                                    .getConstructor()
                                    .newInstance());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method m, Object[] args)
                throws Throwable {
            return backingFields.get(m);
        }

    }

}
