package splumb.messaging.commands;

import splumb.messaging.MapMsgBuilder;
import splumb.net.nio.Client;

import static splumb.messaging.commands.BrokerCommands.*;
import static splumb.messaging.commands.MapFields.*;
import static splumb.messaging.commands.AdminQueues.*;

public class AddQueue implements BrokerCommand {

    private String qName;

    public AddQueue withDestination(String qName) {
        this.qName = qName;
        return this;
    }

    @Override
    public void sendTo(Client netClient) {
        netClient.send(new MapMsgBuilder()
                .withDestination(ADMIN_REQ_Q.qName())
                .addString(COMMAND.name(), ADD_QUEUE.name())
                .addString(DESTINATION.name(), qName)
                .build()
                .toByteArray());
    }
}
