package splumb.messaging;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.protobuf.InvalidProtocolBufferException;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.NativeFrameBuilder;
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
    private EventBus bus;

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
    LocalBroker(LogPublisher logger, EventBus bus) {
        this.logger = logger;
        this.bus = bus;

        bus.register(deadLetterHandler);
        listeners.put(ADMIN_REQ_Q.qName(), this);
        endpoints = new NetEndpoints(logger);
        server = endpoints.newTcpServer(this, new NativeFramer());

        // TODO - this needs to come from config...
        server.listen(8000);
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
        new CommandProcessor(endPoints, bus, listeners).process(message, src);
    }

    class DeadLetterHandler implements InternalMessageSink {
        Multimap<String, Msg> pending = ArrayListMultimap.create();

        @Subscribe
        public void sinkAvailable(QueueAvailableEvent event) {
            // TODO - need to remove - safe while iterating?

            for (Msg msg : pending.get(event.getDestination())) {
                event.getNetConn()
                        .send(new NativeFrameBuilder()
                                .withPayload(msg.toByteArray())
                                .build());
            }
        }

        @Override
        public void receive(Msg message, Client src) {
            pending.put(message.getDestination(), message);
        }
    }
}
