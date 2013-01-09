package splumb.messaging.commands;

import splumb.messaging.MapMsgBuilder;
import splumb.net.nio.Client;

import static splumb.messaging.commands.BrokerCommands.*;
import static splumb.messaging.commands.MapFields.*;

public class AddQueue implements BrokerCommand {

    private String qName;

    public AddQueue withDestination(String qName) {
        this.qName = qName;
        return this;
    }

    @Override
    public void send(Client netClient) {
        netClient.send(new MapMsgBuilder()
                .withDestination(qName)
                .addString(COMMAND.strVal(), ADD_QUEUE.strVal())
                .addString(DESTINATION.strVal(), qName)
                .build()
                .toByteArray());
    }
}
