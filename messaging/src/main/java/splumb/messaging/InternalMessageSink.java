package splumb.messaging;

import splumb.net.nio.Client;
import static splumb.protobuf.BrokerMsg.*;

interface InternalMessageSink {
    void receive(Msg message, Client src);
}
