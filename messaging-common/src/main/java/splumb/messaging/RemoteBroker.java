package splumb.messaging;

import splumb.net.framing.NativeFrameBuilder;
import splumb.net.nio.Client;
import splumb.protobuf.BrokerMsg;

import static splumb.protobuf.BrokerMsg.Msg.Type.*;

class RemoteBroker implements Broker {
    private Client netClient;
    private NativeFrameBuilder frameBuilder = new NativeFrameBuilder();

    public RemoteBroker(Client netClient) {
        this.netClient = netClient;
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
    public void send(Message message) {
        BrokerMsg.Msg.newBuilder().setType(Map);
        //netClient.send();
    }
}
