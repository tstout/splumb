package splumb.messaging;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import splumb.protobuf.BrokerMsg;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static splumb.protobuf.BrokerMsg.*;

public class MapMsgBuilder {

    private final Map<Class<?>, Function<Pair, KeyValue.Builder>> typeMap = new MapMaker().weakKeys().makeMap();
    private final BrokerMsg.MapMsg.Builder mapMsgBuilder = BrokerMsg.MapMsg.newBuilder();
    private final BrokerMsg.Msg.Builder msgBuilder = BrokerMsg.Msg.newBuilder();

    public MapMsgBuilder() {
        typeMap.put(Integer.class, Fn.assignInt32());
        typeMap.put(String.class, Fn.assignString());
        typeMap.put(Boolean.class, Fn.assignBool());
    }

    public <T> MapMsgBuilder add(String key, T value) {

        Function<Pair, KeyValue.Builder> f = typeMap.get(value.getClass());

        checkNotNull(f, "MapMsg does not support the type %s", value.getClass());

        mapMsgBuilder.addItems(f.apply(new Pair(KeyValue.newBuilder(), value))
                .setKey(key)
                .build());

        return this;
    }

    public MapMsgBuilder withDestination(String destination) {
        msgBuilder.setDestination(destination);
        return this;
    }

    public Msg build() {
        return msgBuilder
                .setType(Msg.Type.Map)
                .setMapMsg(mapMsgBuilder)
                .build();
    }

    static final class Fn {

        public static Function<Pair, KeyValue.Builder> assignInt32() {
            return new Function<Pair, KeyValue.Builder>() {

                @Override
                public KeyValue.Builder apply(Pair input) {
                    input.builder
                            .setType(KeyValue.Type.Int32)
                            .setInt32Value((Integer) input.value);

                    return input.builder;
                }
            };
        }

        public static Function<Pair, KeyValue.Builder> assignString() {
            return new Function<Pair, KeyValue.Builder>() {

                @Override
                public KeyValue.Builder apply(Pair input) {
                    input.builder
                            .setStringValue((String) input.value)
                            .setType(KeyValue.Type.String);

                    return input.builder;
                }
            };
        }

        public static Function<Pair, KeyValue.Builder> assignBool() {
            return new Function<Pair, KeyValue.Builder>() {

                @Override
                public KeyValue.Builder apply(Pair input) {
                    input.builder
                            .setBoolValue((Boolean) input.value)
                            .setType(KeyValue.Type.Boolean);

                    return input.builder;
                }
            };
        }
    }

    class Pair {
        KeyValue.Builder builder;
        Object value;

        Pair(KeyValue.Builder builder, Object value) {
            this.builder = builder;
            this.value = value;
        }
    }
}
