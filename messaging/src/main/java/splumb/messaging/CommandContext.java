package splumb.messaging;

import com.google.common.collect.Multimap;
import com.google.common.eventbus.EventBus;
import splumb.net.nio.Client;
import splumb.protobuf.BrokerMsg;

import java.util.Map;

class CommandContext {
    BrokerMsg.MapMsg msg;
    Client src;
    String destination;
    // TODO - not sure endpoints is necessary...
    Multimap<String, Client> endPoints;


    //
    // Consider using an event bus event to trigger a chain invocation instead of
    // maintaining a listener map.
    //
    Map<String, InternalMessageSink> listeners;
    EventBus bus;

    private CommandContext(BrokerMsg.MapMsg msg, Client src, Multimap<String, Client> endPoints, String destination,
                           EventBus bus, Map<String, InternalMessageSink> listeners) {
        this.msg = msg;
        this.src = src;
        this.destination = destination;
        this.endPoints = endPoints;
        this.bus = bus;
        this.listeners = listeners;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private BrokerMsg.MapMsg msg;
        private Client src;
        private Multimap<String, Client> endPoints;
        private String destination;
        private EventBus bus;
        private Map<String, InternalMessageSink> listeners;

        Builder withListeners(Map<String, InternalMessageSink> listeners) {
            this.listeners = listeners;
            return this;
        }

        Builder withBus(EventBus bus) {
            this.bus = bus;
            return this;
        }

        Builder withDestination(String destination) {
            this.destination = destination;
            return this;
        }

        Builder withMsg(BrokerMsg.MapMsg msg) {
            this.msg = msg;
            return this;
        }

        Builder withSrc(Client src) {
            this.src = src;
            return this;
        }

        Builder withEndpoints(Multimap<String, Client> endPoints) {
            this.endPoints = endPoints;
            return this;
        }

        CommandContext build() {
            return new CommandContext(msg, src, endPoints, destination, bus, listeners);
        }
    }
}
