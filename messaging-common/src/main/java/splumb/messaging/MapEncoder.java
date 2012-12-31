package splumb.messaging;

import splumb.protobuf.BrokerMsg;

import java.util.Map;

class MapEncoder<V> {
    private Map<String, V> map;
    private

    MapEncoder(Map<String, V> map) {
        this.map = map;
    }

    BrokerMsg.Msg encode() {
        for (Map.Entry<String, V> entry : map.entrySet()) {

        }

        return null;
    }



}
