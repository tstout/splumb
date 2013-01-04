package splumb.messaging;

import splumb.protobuf.BrokerMsg;

import static com.google.common.base.Preconditions.checkNotNull;
import static splumb.protobuf.BrokerMsg.*;

public class MapMsgBuilder {
    private BrokerMsg.MapMsg.Builder mapMsgBuilder = BrokerMsg.MapMsg.newBuilder();
    private BrokerMsg.Msg.Builder msgBuilder = BrokerMsg.Msg.newBuilder();
    private String destination;

    public MapMsgBuilder addInt32(String key, int value) {

        KeyValue kv = KeyValue
                .newBuilder()
                .setType(KeyValue.Type.Int32)
                .setKey(key)
                .setInt32Value(value)
                .build();

        mapMsgBuilder.addItems(kv);

        return this;
    }

    public MapMsgBuilder addString(String key, String value) {
        KeyValue kv = KeyValue
                .newBuilder()
                .setType(KeyValue.Type.String)
                .setKey(key)
                .setStringValue(value)
                .build();

        mapMsgBuilder.addItems(kv);

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
}
