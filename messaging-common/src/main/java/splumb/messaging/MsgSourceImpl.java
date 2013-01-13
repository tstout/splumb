package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

class MsgSourceImpl implements MessageSource {
    private BrokerClient broker;

    MsgSourceImpl(BrokerClient broker) {
        this.broker = broker;
    }

    @Override
    public void send(Msg message) {
        broker.send(message);
    }
}
