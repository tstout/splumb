package splumb.messaging.commands;

import splumb.net.nio.Client;

public interface BrokerCommand {

    void sendTo(Client netClient);
}


