package splumb.messaging;

import com.google.common.collect.Multimap;
import splumb.net.nio.Client;
import splumb.protobuf.BrokerMsg;

class CommandContext {
    BrokerMsg.MapMsg msg;
    Client src;
    Multimap<String, Client> endPoints;

    private CommandContext(BrokerMsg.MapMsg msg, Client src, Multimap<String, Client> endPoints) {
        this.msg = msg;
        this.src = src;
        this.endPoints = endPoints;
    }

    static Builder builder() {
        return new Builder();
    }

    static class Builder {
        private BrokerMsg.MapMsg msg;
        private Client src;
        private Multimap<String, Client> endPoints;

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
            return new CommandContext(msg, src, endPoints);
        }
    }
}
