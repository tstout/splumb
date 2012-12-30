package splumb.messaging;

public interface MessageSink {
    void receive(Message message);
}
