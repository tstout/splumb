package splumb.messaging;

import splumb.common.logging.LogPublisher;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;

import javax.inject.Inject;

import static splumb.protobuf.BrokerMsg.*;

class RemoteBroker implements Broker, MsgHandler {
    private BrokerConfig brokerConfig;
    private Client netClient;
    private NetEndpoints netEndpoints;
    private LogPublisher logger;

    @Inject
    public RemoteBroker(LogPublisher logger, BrokerConfig brokerConfig) {
        this.brokerConfig = brokerConfig;
        this.logger = logger;
        netEndpoints = new NetEndpoints(logger);
        netClient = new BrokerConnection(brokerConfig).createConnection(netEndpoints, this);
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Msg message) {
        //BrokerMsg.Msg.newBuilder().setType(Msg.Type.Map);
        //netClient.send();
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
