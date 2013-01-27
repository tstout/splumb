package splumb.messaging;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.MapMaker;
import splumb.protobuf.BrokerMsg;

import java.util.Map;

import static com.google.common.collect.FluentIterable.*;
import static splumb.protobuf.BrokerMsg.*;

public class MapMsgParser {
    private MapMsg msg;
    //private java.lang.reflect.Type genericReturnType;

    private final Map<Class<?>, Function<KeyValue, ?>> typeMap = new MapMaker().weakKeys().makeMap();

    interface Action {
        void apply(KeyValue keyValue);
    }

    public MapMsgParser(MapMsg msg) {
        this.msg = msg;
        typeMap.put(Integer.class, Fn.getInt32());
        typeMap.put(String.class, Fn.getString());
        typeMap.put(Boolean.class, Fn.getBool());

//        try {
//            genericReturnType = getClass().getMethod("get", String.class).getGenericReturnType();
//        } catch (NoSuchMethodException e) {
//            propagate(e);
//        }
    }

    public <T> T get(String key, Class<T> clazz) {

        // Guava's TypeToken appears to support what I was trying to do here, but it was not working...
        // revisit later...it would be nice not to have to pass in the clazz parm.
        //Class<?> clazz = TypeToken.of(genericReturnType).resolveType(genericReturnType).getType().getClass();

        Optional<KeyValue> optVal = from(msg.getItemsList())
                .filter(Fn.keyMatches(key)).first();

        return clazz.cast(typeMap.get(clazz).apply(optVal.get()));
    }

    static final class Fn {

        static Function<KeyValue, Integer> getInt32() {
            return new Function<KeyValue, Integer>() {

                @Override
                public  Integer apply(KeyValue keyValue) {
                    return keyValue.getInt32Value();
                }
            };
        }

        static Function<KeyValue, String> getString() {
            return new Function<KeyValue, String>() {

                @Override
                public String apply(KeyValue keyValue) {
                    return keyValue.getStringValue();
                }
            };
        }

        static Function<KeyValue, Boolean> getBool() {
            return new Function<KeyValue, Boolean>() {

                @Override
                public Boolean apply(KeyValue keyValue) {
                    return keyValue.getBoolValue();
                }
            };
        }

        static Predicate<KeyValue> keyMatches(final String key) {
            return new Predicate<BrokerMsg.KeyValue>() {
                @Override
                public boolean apply(BrokerMsg.KeyValue input) {
                    return input.getKey().equals(key);
                }
            };
        }
    }
}
