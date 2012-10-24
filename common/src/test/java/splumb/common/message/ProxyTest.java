package splumb.common.message;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProxyTest
{
    //PropFactory pFactory = new PropFactory();

    interface ModelX
    {
        Prop<Integer> id();
        Prop<String> name();
        Prop<List<String>> names();
    }

    @Test
    public void SetInteger()
    {
        ModelX m = new PropFactory().newProp(ModelX.class);

        m.id().set(29);
        assertTrue(m.id().get() == 29);
    }

    @Test
    public void SetString()
    {
        ModelX m = new PropFactory().newProp(ModelX.class);

        m.name().set("Hello");
        assertTrue(m.name().get() == "Hello");
    }

    @Test
    public void SetList()
    {
        ModelX m = new PropFactory().newProp(ModelX.class);

        m.names().set(new ArrayList<String>());
        m.names().get().add("first name");
        m.names().get().add("second name");
        m.names().get().add("thrid name");

//              for (String n : m.names().get())
//              {
//                      System.out.printf("Name is: %s\n", n);
//              }

        assertTrue(m.names().get().size() == 3);
    }

    @Test
    public void GenTypeTest()
    {
        //GenType<String> t = new GenType<String>();
    }

//    class GenType<T>
//    {
//        public GenType()
//        {
//            try
//            {
//                val = new TypeReference<T>(){}.newInstance();
//            } catch (NoSuchMethodException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IllegalAccessException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (InvocationTargetException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (InstantiationException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//
//        private T val;
//    }
}
