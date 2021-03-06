package splumb.messaging;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.NativeFrameBuilder;
import splumb.net.framing.NativeFramer;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;
import splumb.net.nio.Server;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Functions.*;
import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Maps.*;
import static splumb.messaging.commands.AdminQueues.*;
import static splumb.protobuf.BrokerMsg.*;

//
// Not sure I like the broker implementing MsgHandler and MessageSink...
//
class BrokerService implements Broker, MsgHandler, InternalMessageSink {

    private NetEndpoints endpoints;
    private LogPublisher logger;
    private Server server;
    private Map<String, InternalMessageSink> listeners = newHashMap();
    private DeadLetterHandler deadLetterHandler = new DeadLetterHandler();
    private Multimap<String, Client> endPoints = ArrayListMultimap.create();
    private EventBus bus;

    //
    // TODO - need to maintain a list of connections to all brokers (except this one)
    // listed in the MsgBrokers table.  If there is not a handler for the destination,
    // the message needs to be forwarded to the other brokers if the destination is a queue.
    // If the destination is a topic, then the message should be forwarded to all other brokers,
    // even if this broker had a matching listener.
    // In addition, addSink needs to accept a Destination class that specifies a schemaName and a
    // queue or topic type.
    //
    @Inject
    BrokerService(LogPublisher logger, EventBus bus) {
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
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        try {
            Msg m = Msg.parseFrom(msg);

            forMap(listeners, deadLetterHandler)
                    .apply(m.getDestination())
                    .receive(m, sender);
        }
        catch (Exception e) {
            throw propagate(e);
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
            //
            // New queue available...send any pending messages.
            //
            for (Iterator<Msg> it = pending.get(event.getDestination()).iterator(); it.hasNext();) {
                event.getNetConn()
                        .send(new NativeFrameBuilder()
                                .withPayload(it.next().toByteArray())
                                .build());
                it.remove();
            }
        }

        @Override
        public void receive(Msg message, Client src) {
            pending.put(message.getDestination(), message);
        }
    }
}
