package splumb.messaging;

import com.google.common.base.Predicate;
import splumb.protobuf.BrokerMsg;

import static com.google.common.collect.FluentIterable.*;

public class MapMessages {

    public static int getInt32(BrokerMsg.MapMsg msg,  String key) {
        return from(msg.getItemsList())
                .filter(Fn.keyMatches(key))
                .first()
                .get()
                .getInt32Value();
    }

    public static String getString(BrokerMsg.MapMsg msg, String key) {
        return from(msg.getItemsList())
                .filter(Fn.keyMatches(key))
                .first()
                .get()
                .getStringValue();
    }

    public static Boolean getBool(BrokerMsg.MapMsg msg, String key) {
        return from(msg.getItemsList())
                .filter(Fn.keyMatches(key))
                .first()
                .get()
                .getBoolValue();
    }

    //Optional<BrokerMsg.KeyValue> getValue(BrokerMsg)

    static class Fn {
        static Predicate<BrokerMsg.KeyValue> keyMatches(final String key) {
            return new Predicate<BrokerMsg.KeyValue>() {
                @Override
                public boolean apply(BrokerMsg.KeyValue input) {
                    return input.getKey().equals(key);
                }
            };
        }
    }
}
