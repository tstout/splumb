package splumb.messaging;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.google.common.collect.Lists.*;

class ClientBrokerConfig implements BrokerConfig {

    private final int port;
    private final String host;

    @Inject
    ClientBrokerConfig(@Named("brokerHost") String host,
                       @Named("brokerPort") int port) {
        this.port = port;
        this.host = host;
    }

    @Override
    public List<BrokerLocation> brokers() {
        return newArrayList(new BrokerLocation(host, port));
    }
}
