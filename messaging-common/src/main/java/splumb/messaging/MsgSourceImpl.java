package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

class MsgSourceImpl implements MessageSource {
    private RemoteBroker broker;

    MsgSourceImpl(RemoteBroker broker) {
        this.broker = broker;
    }

    @Override
    public void send(Msg message) {

    }
}
