package splumb.messaging;

import splumb.common.logging.LogPublisher;

import javax.inject.Inject;

public class MessageEndpoints {
    private LogPublisher logger;
    private RemoteBroker remoteBroker;

    @Inject
    public MessageEndpoints(LogPublisher logger, RemoteBroker remoteBroker) {
        this.logger = logger;
        this.remoteBroker = remoteBroker;
    }

    public void registerSink(String destination, MessageSink sink) {
    }

    public MessageSource createSource(String destination) {
        return new MsgSourceImpl(remoteBroker);
    }
}
