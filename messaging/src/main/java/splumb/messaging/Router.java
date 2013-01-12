package splumb.messaging;

import static splumb.protobuf.BrokerMsg.*;

interface Router {
    void route(Msg msg);
}
