package splumb.messaging;

public interface Broker {
    void addQueue(String qName);

    void addTopic(String topicName);

    void addSink(String destination, MessageSink sink);

    void send(Message message);
}
