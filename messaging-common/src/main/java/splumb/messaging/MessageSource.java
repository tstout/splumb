package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

public interface MessageSource {
    void send(Msg message);
}
