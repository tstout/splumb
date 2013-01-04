package splumb.messaging;

import splumb.protobuf.BrokerMsg;

import static splumb.protobuf.BrokerMsg.*;

public class MapMsgBuilder {
    private BrokerMsg.MapMsg.Builder mapMsgBuilder = BrokerMsg.MapMsg.newBuilder();

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

    public Msg build() {
        return Msg.newBuilder()
                .setType(Msg.Type.Map)
                .setMapMsg(mapMsgBuilder)
                .build();
    }

}
