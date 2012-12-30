package splumb.messaging;

import splumb.common.logging.LogPublisher;

public class MessageEndpoints {
    private LogPublisher logger;

    public MessageEndpoints(LogPublisher logger) {
        this.logger = logger;
    }

    public void registerSink(String destination, MessageSink sink) {

    }

}
