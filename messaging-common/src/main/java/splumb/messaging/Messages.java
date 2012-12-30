package splumb.messaging;

public class Messages {
    public MessageSink newSink(String destination, MessageSink sink) {
        return new Sink(destination);
    }
}
