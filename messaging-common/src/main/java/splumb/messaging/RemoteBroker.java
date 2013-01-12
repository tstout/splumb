package splumb.messaging;

import com.google.protobuf.InvalidProtocolBufferException;
import splumb.common.logging.LogPublisher;
import splumb.messaging.commands.AddQueue;
import splumb.net.framing.NativeFrameBuilder;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Functions.*;
import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Maps.*;
import static splumb.protobuf.BrokerMsg.*;

class RemoteBroker implements Broker, MsgHandler {
    private BrokerConfig brokerConfig;
    private Client brokerConn;
    private NetEndpoints netEndpoints;
    private LogPublisher logger;
    private Map<String, MessageSink> sinks = newHashMap();

    @Inject
    public RemoteBroker(LogPublisher logger, BrokerConfig brokerConfig) {
        this.brokerConfig = brokerConfig;
        this.logger = logger;
        netEndpoints = new NetEndpoints(logger);
        brokerConn = new BrokerConnection(brokerConfig).createConnection(netEndpoints, this);
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
        sinks.put(destination, sink);

        new AddQueue()
                .withDestination(destination)
                .sendTo(brokerConn);
    }

    @Override
    public void send(Msg message) {
        brokerConn.send(new NativeFrameBuilder()
                .withPayload(message.toByteArray())
                .build());
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        try {
            Msg m = Msg.parseFrom(msg);
            String destination = m.getDestination();

            forMap(sinks, new DeadLetterSink())
                    .apply(destination)
                    .receive(m);

        } catch (InvalidProtocolBufferException e) {
            propagate(e);
        }
    }

    class DeadLetterSink implements MessageSink {

        @Override
        public void receive(Msg message) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }


    class Fn {
    }

}
