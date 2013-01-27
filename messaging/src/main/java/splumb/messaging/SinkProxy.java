package splumb.messaging;

import splumb.net.framing.NativeFrameBuilder;
import splumb.net.nio.Client;
import splumb.protobuf.BrokerMsg;

/**
 * Forwards messages received by the broker to a
 * (usually) remote listener.
 */
class SinkProxy implements InternalMessageSink {
    private Client connection;

    SinkProxy(Client connection) {
        this.connection = connection;
    }

    @Override
    public void receive(BrokerMsg.Msg message, Client src) {
        connection.send(new NativeFrameBuilder()
                .withPayload(message.toByteArray())
                .build());
    }
}
