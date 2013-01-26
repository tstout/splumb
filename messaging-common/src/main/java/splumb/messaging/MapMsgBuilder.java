package splumb.messaging;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import splumb.protobuf.BrokerMsg;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.ImmutableMap.*;
import static splumb.protobuf.BrokerMsg.*;

public class MapMsgBuilder {

    private final ImmutableMap<?, Function<Pair, KeyValue.Builder>> TYPE_MAP =
            of(Integer.class, Fn.assignInt32(),
                    String.class, Fn.assignString(),
                    Boolean.class, Fn.assignBool());

    private BrokerMsg.MapMsg.Builder mapMsgBuilder = BrokerMsg.MapMsg.newBuilder();
    private BrokerMsg.Msg.Builder msgBuilder = BrokerMsg.Msg.newBuilder();
    private String destination;

    public <T> MapMsgBuilder add(String key, T value) {

        Function<Pair, KeyValue.Builder> f = TYPE_MAP.get(value.getClass());

        if (f == null) {
            throw new RuntimeException(String.format("MapMsg does not support the type %s", value.getClass()));
        }

        mapMsgBuilder.addItems(f.apply(new Pair(KeyValue.newBuilder(), value))
                .setKey(key)
                .build());

        return this;
    }

    public MapMsgBuilder withDestination(String destination) {
        this.destination = destination;
        msgBuilder.setDestination(destination);
        return this;
    }

    public Msg build() {
        checkNotNull(destination, "Destination is required");

        return msgBuilder
                .setDestination(destination)
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
