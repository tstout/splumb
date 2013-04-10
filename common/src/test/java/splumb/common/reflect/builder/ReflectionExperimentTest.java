package splumb.common.reflect.builder;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class ReflectionExperimentTest {

    @Test
    public void methodParamTest() {
        for (Method m : TestClass.class.getMethods()) {
            if (!m.getName().equals("foo")) {
                continue;
            }
            if (m.getParameterAnnotations().length != 0) {
                Annotation[] parmOne = m.getParameterAnnotations()[0];
                Annotation a = parmOne[0];
            }
        }


        Annotation[][] parms = TestClass.class.getMethods()[0].getParameterAnnotations();
        Annotation[] parmOne = parms[0];
    }

    class TestClass {

        public void foo(@BuilderParam int intParm) {
        }
    }
}
