package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

/**
 * I kinda like this...try and make this work.
 */
public interface Broker {

    void addSink(String destination, MessageSink sink);

    void send(Msg message);
}
