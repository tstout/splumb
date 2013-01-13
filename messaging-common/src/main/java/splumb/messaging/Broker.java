package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

public interface Broker {

    void addSink(String destination, MessageSink sink);

    void send(Msg message);
}
