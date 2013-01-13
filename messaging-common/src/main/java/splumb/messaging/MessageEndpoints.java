package splumb.messaging;

import splumb.common.logging.LogPublisher;

import javax.inject.Inject;

public class MessageEndpoints {
    private LogPublisher logger;
    private BrokerClient remoteBroker;

    @Inject
    public MessageEndpoints(LogPublisher logger, BrokerClient remoteBroker) {
        this.logger = logger;
        this.remoteBroker = remoteBroker;
    }

    public MessageEndpoints registerSink(String destination, MessageSink sink) {
        remoteBroker.addSink(destination, sink);
        return this;
    }

    public MessageSource createSource(String destination) {
        return new MsgSourceImpl(remoteBroker);
    }
}
