package splumb.messaging;

import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.google.common.base.Throwables.propagate;

class BrokerConnection {
    private BrokerConfig brokerConfig;
    private BrokerLocation brokerLocation;

    public BrokerConnection(BrokerConfig brokerConfig) {
        this.brokerConfig = brokerConfig;

        // TODO...need to decide what to do about multiple brokers here...
        // Should probably
        brokerLocation = brokerConfig.brokers().size() == 0 ? new BrokerLocation("127.0.0.1", 8000) :
                brokerConfig.brokers().get(0);
    }

    Client createConnection(NetEndpoints endpoints, MsgHandler handler) {
        Client client = null;
        try {
            client = endpoints.clientBuilder()
                    .withMsgHandler(handler)
                    .withPort(brokerLocation.port())
                    .withServerAddress(InetAddress.getByName(brokerLocation.host()))
                    .build();
        } catch (UnknownHostException e) {
           throw propagate(e);
        }
        return client;
    }
}
