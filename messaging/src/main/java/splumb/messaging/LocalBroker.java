package splumb.messaging;


import com.google.protobuf.InvalidProtocolBufferException;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.NativeFramer;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;
import splumb.net.nio.Server;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Maps.*;
import static splumb.protobuf.BrokerMsg.*;

class LocalBroker implements Broker, MsgHandler, MessageSink {

    private NetEndpoints endpoints;
    private LogPublisher logger;
    private Server server;
    private Map<String, MessageSink> listeners = newHashMap();

    //
    // TODO - need to maintain a list of connections to all borkers (except this one)
    // listed in the MsgBrokers table.  If there is not a handler for the destination,
    // the message needs to be forwarded to the other brokers if the destination is a queue.
    // If the destination is a topic, then the message should be forwarded to all other brokers,
    // even if this broker had a matching listener.
    // In addition, addSink needs to accept a Destination class that specifies a name and a
    // queue or topic type.
    //
    @Inject
    LocalBroker(LogPublisher logger) {
        listeners.put(MessagingConstants.ADMIN_REQ_Q, this);
        this.logger = logger;
        endpoints = new NetEndpoints(logger);
        server = endpoints.newTcpServer(this, new NativeFramer());
        server.listen(8000);
    }

    @Override
    public void addQueue(String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addTopic(String topicName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addSink(String destination, MessageSink sink) {
        listeners.put(destination, sink);
    }

    @Override
    public void send(Msg message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        try {
            Msg m = Msg.parseFrom(msg);
            listeners.get(m.getDestination()).receive(m);
        } catch (InvalidProtocolBufferException e) {
            propagate(e);
        }
    }

    class ForwardingHandler implements MessageSink {

        @Override
        public void receive(Msg message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }


    @Override
    public void receive(Msg message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
