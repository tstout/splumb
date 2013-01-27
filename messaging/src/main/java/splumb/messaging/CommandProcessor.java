package splumb.messaging;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import splumb.messaging.commands.MapFields;
import splumb.net.nio.Client;

import java.util.Map;

import static com.google.common.base.Functions.*;
import static com.google.common.collect.ImmutableMap.*;
import static splumb.messaging.commands.BrokerCommands.*;
import static splumb.protobuf.BrokerMsg.*;

class CommandProcessor {
    private static final ImmutableMap<String, CommandSink> sinks =
            of(ADD_QUEUE.name(), AddQueueSink.newSink(),
               ADD_TOPIC.name(), AddTopicSink.newSink());

    private Multimap<String, Client> endpoints;
    private EventBus bus;
    private Map<String, InternalMessageSink> listeners;

    CommandProcessor(Multimap<String, Client> endpoints, EventBus bus, Map<String, InternalMessageSink> listeners) {
        this.endpoints = endpoints;
        this.bus = bus;
        this.listeners = listeners;
    }

    void process(Msg msg, Client src) {
        MapMsgParser mp = new MapMsgParser(msg.getMapMsg());

        forMap(sinks)
                .apply(mp.get(MapFields.COMMAND.name(), String.class))
                        .receive(CommandContext.builder()
                                .withEndpoints(endpoints)
                                .withMsg(msg.getMapMsg())
                                .withSrc(src)
                                .withListeners(listeners)
                                .withBus(bus)
                                .withDestination(msg.getDestination())
                                .build());
    }
}

interface CommandSink {
    void receive(CommandContext context);
}

class AddQueueSink implements CommandSink {

    static CommandSink newSink() {
        return new AddQueueSink();
    }

    @Override
    public void receive(CommandContext context) {
        MapMsgParser mp = new MapMsgParser(context.msg);
        String destination  = mp.get(MapFields.DESTINATION.name(), String.class);
        context.listeners.put(destination, new SinkProxy(context.src));
        context.endPoints.put(destination, context.src);
        context.bus.post(new QueueAvailableEvent(destination, context.src));
    }
}

class AddTopicSink implements CommandSink {

    static CommandSink newSink() {
        return new AddTopicSink();
    }

    @Override
    public void receive(CommandContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}