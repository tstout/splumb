package splumb.messaging;

import com.google.common.collect.ImmutableMap;
import com.google.common.eventbus.EventBus;
import com.google.protobuf.InvalidProtocolBufferException;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;

import javax.inject.Inject;

import static com.google.common.base.Throwables.*;
import static splumb.protobuf.BrokerMsg.*;
import static splumb.protobuf.BrokerMsg.AdminMsg.*;

class AdminMsgRouter implements MsgHandler {
    private EventBus bus;

    @Inject
    AdminMsgRouter(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        try {
            AdminMsg adminMsg = AdminMsg.parseFrom(msg);

            bus.post(Selector
                    .fromType(adminMsg.getType())
                    .select(adminMsg));

        } catch (InvalidProtocolBufferException e) {
            throw propagate(e);
        }
    }

    // Java 8 lambdas will make this cleaner...end of 2013?
    enum Selector implements MsgSelector<AdminMsg> {
        LIST_Q_REQ_SELECTOR() {
            @Override
            public Object select(AdminMsg msg) {
                return msg.getListQueueReq();
            }
        },
        LIST_Q_RESP_SELECTOR() {
            @Override
            public Object select(AdminMsg msg) {
                return msg.getListQueueResp();
            }
        },
        LIST_BROKER_REQ_SELECTOR() {
            @Override
            public Object select(AdminMsg msg) {
                return msg.getListBrokersReq();
            }
        },
        LIST_BROKER_RESP_SELECTOR() {
            @Override
            public Object select(AdminMsg msg) {
                return msg.getListBrokersResp();
            }
        };

        private static ImmutableMap<AdminMsg.Type, Selector> typeMap =
                new ImmutableMap.Builder<AdminMsg.Type, Selector>()
                        .put(Type.ListQueuesReq, LIST_Q_REQ_SELECTOR)
                        .put(Type.ListQueuesResp, LIST_Q_RESP_SELECTOR)
                        .put(Type.ListBrokersReq, LIST_BROKER_REQ_SELECTOR)
                        .put(Type.ListBrokersResp, LIST_BROKER_RESP_SELECTOR)
                        .build();

        static MsgSelector<AdminMsg> fromType(AdminMsg.Type t) {
            return typeMap.get(t);
        }

        //abstract Object select(AdminMsg msg);
    }
}
