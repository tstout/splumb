package splumb.messaging;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.protobuf.InvalidProtocolBufferException;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.NativeFramer;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;
import splumb.net.nio.Server;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Functions.*;
import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Maps.*;
import static splumb.messaging.commands.AdminQueues.*;
import static splumb.protobuf.BrokerMsg.*;

//
// Not sure I like the broker implementing MsgHandler and MessageSink...
//
class LocalBroker implements Broker, MsgHandler, InternalMessageSink {

    private NetEndpoints endpoints;
    private LogPublisher logger;
    private Server server;
    private Map<String, InternalMessageSink> listeners = newHashMap();
    private DeadLetterHandler deadLetterHandler = new DeadLetterHandler();
    private Multimap<String, Client> endPoints = ArrayListMultimap.create();

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
        this.logger = logger;

        listeners.put(ADMIN_REQ_Q.qName(), this);
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
        //listeners.put(destination, sink);
    }

    @Override
    public void send(Msg message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        try {
            Msg m = Msg.parseFrom(msg);

            forMap(listeners, deadLetterHandler)
                    .apply(m.getDestination())
                    .receive(m, sender);

        } catch (InvalidProtocolBufferException e) {
            propagate(e);
        }
    }

    @Override
    public void receive(Msg message, Client src) {
        //
        // Process Admin Q requests...
        //
        new CommandProcessor(endPoints).process(message, src);
    }

    class DeadLetterHandler implements InternalMessageSink {
        Multimap<String, Msg> pending = ArrayListMultimap.create();

        void sinkAvailable(String destination, Client dest) {
            for (Msg msg : pending.get(destination)) {
                dest.send(msg.toByteArray());
            }
        }

        @Override
        public void receive(Msg message, Client src) {
            pending.put(message.getDestination(), message);
        }
    }
}
