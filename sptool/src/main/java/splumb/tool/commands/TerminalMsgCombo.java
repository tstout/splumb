package splumb.tool.commands;

import com.google.common.eventbus.EventBus;
import splumb.messaging.MessageEndpoints;
import splumb.messaging.MessageSink;
import splumb.messaging.MessageSource;
import splumb.messaging.commands.AdminQueues;
import splumb.protobuf.BrokerMsg;

import javax.inject.Inject;

class TerminalMsgCombo {
    private MessageSource msgSource;
    private EventBus bus;

    @Inject
    TerminalMsgCombo(MessageEndpoints msgEndpoints, EventBus bus) {
        this.bus = bus;
        this.msgSource = msgEndpoints.createSource(AdminQueues.ADMIN_REQ_Q.qName());

        // TODO - need to register this process to deal with multiple concurrent
        // clients.
        msgEndpoints.registerSink(AdminQueues.ADMIN_RESP_Q.qName(), new Sink());
    }

    class Sink implements MessageSink {

        @Override
        public void receive(BrokerMsg.Msg message) {

        }
    }
}


