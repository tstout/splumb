package splumb.messaging;


import splumb.common.logging.LogPublisher;
import splumb.net.framing.NativeFramer;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;
import splumb.net.nio.Server;

import javax.inject.Inject;

class LocalBroker implements Broker, MsgHandler {

    private NetEndpoints endpoints;
    private LogPublisher logger;
    private Server server;

    @Inject
    LocalBroker(LogPublisher logger) {
        this.logger = logger;
        endpoints = new NetEndpoints(logger);
        server = endpoints.newTcpServer(this, new NativeFramer());
        server.listen(8000);
    }

    @Override
    public void addQueue(String qName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addTopic(String topicName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addSink(String destination, MessageSink sink) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void msgAvailable(Client sender, byte[] msg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
