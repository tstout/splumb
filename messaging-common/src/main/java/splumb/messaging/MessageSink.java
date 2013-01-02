package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

public interface MessageSink {
    void receive(Msg message);
}
