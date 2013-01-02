package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

class Sink implements MessageSink {
    private String destination;

    public Sink(String destination) {
        this.destination = destination;
    }

    @Override
    public void receive(Msg message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
