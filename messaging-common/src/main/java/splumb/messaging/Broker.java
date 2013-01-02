package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

public interface Broker {
    void addQueue(String qName);

    void addTopic(String topicName);

    void addSink(String destination, MessageSink sink);

    void send(Msg message);
}
