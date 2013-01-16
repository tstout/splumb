package splumb.messaging;

import com.google.common.base.Predicate;
import splumb.protobuf.BrokerMsg;

import static com.google.common.collect.FluentIterable.from;
import static splumb.protobuf.BrokerMsg.*;

public class MapMsgParser {
    private MapMsg msg;

    public MapMsgParser(MapMsg msg) {
        this.msg = msg;
    }

    public int getInt32(String key) {
        return from(msg.getItemsList())
                .filter(Fn.keyMatches(key))
                .first()
                .get()
                .getInt32Value();
    }

    public String getString(String key) {
        return from(msg.getItemsList())
                .filter(Fn.keyMatches(key))
                .first()
                .get()
                .getStringValue();
    }

    public Boolean getBool(String key) {
        return from(msg.getItemsList())
                .filter(Fn.keyMatches(key))
                .first()
                .get()
                .getBoolValue();
    }

    //Optional<BrokerMsg.KeyValue> getValue(BrokerMsg)

    static class Fn {
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
