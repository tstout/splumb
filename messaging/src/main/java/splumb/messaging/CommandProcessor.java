package splumb.messaging;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import splumb.net.nio.Client;

import static com.google.common.base.Functions.*;
import static com.google.common.collect.ImmutableMap.*;
import static splumb.messaging.commands.BrokerCommands.*;
import static splumb.protobuf.BrokerMsg.*;

class CommandProcessor {
    private static final ImmutableMap<String, CommandSink> sinks =
            of(ADD_QUEUE.name(), AddQueueSink.newSink(),
               ADD_TOPIC.name(), AddTopicSink.newSink());

    private Multimap<String, Client> endpoints;

    CommandProcessor(Multimap<String, Client> endpoints) {
        this.endpoints = endpoints;
    }

    void process(Msg msg, Client src) {
        forMap(sinks)
                .apply(msg.getDestination())
                .receive(CommandContext.builder()
                        .withEndpoints(endpoints)
                        .withMsg(msg.getMapMsg())
                        .withSrc(src)
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