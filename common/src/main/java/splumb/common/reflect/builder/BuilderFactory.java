package splumb.common.reflect.builder;

import com.google.common.reflect.Reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class BuilderFactory {

    public <T extends Builder<T>> T create(Class<T> clazz) {
        return Reflection.newProxy(clazz, new BuilderHandler<T>(clazz));
    }

    class BuilderHandler<T> implements InvocationHandler {
        private Class<T> intfClass;

        BuilderHandler(Class<T> intfClass) {
            this.intfClass = intfClass;
        }

        private Map<Method, Object[]> backingFields = newHashMap();

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

            //method.getParameterAnnotations()[0][0].annotationType().

            if (method.getName().equals("build")) {

            } else {
                backingFields.put(method, objects);
            }

            return o;
        }
    }

}
