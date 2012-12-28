package splumb.net.nio;

import com.google.common.eventbus.EventBus;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.Framer;
import splumb.net.framing.NativeFramer;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static com.google.common.base.Preconditions.*;

/**
 * Provides factories for creating network endpoints.
 */
public class NetEndpoints {

    private LogPublisher logger;
    private NIOSelect selector;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private EventBus bus = new EventBus();

    public NetEndpoints(LogPublisher logger) {
        this.logger = logger;
        selector = new NIOSelect(logger, bus);
    }

    public Server newTcpServer(MsgHandler handler, Framer framer) {
        return new TCPServer(selector, handler, framer);
    }

    public Client newTcpClient(InetAddress serverAddress, int port, MsgHandler handler, Framer framer) {
        TCPClient client = new TCPClient(serverAddress, port, handler, selector, scheduler, framer);
        bus.register(client);
        return client;
    }

    public ClientBuilder clientBuilder() {
        return new ClientBuilder(this);
    }

    public static class ClientBuilder {
        private int port;
        private MsgHandler msgHandler;
        private Framer framer;
        private InetAddress serverAddress;
        private NetEndpoints endpoints;

        private ClientBuilder(NetEndpoints endpoints) {
            checkNotNull(endpoints);

            this.endpoints = endpoints;
        }

        public Client build() {
            checkState(port != 0, "port is required");
            checkNotNull(msgHandler, "Msg Handler is required");

            return endpoints.newTcpClient(
                    serverAddress == null ? NetConstants.LOCAL_HOST : serverAddress,
                    port,
                    msgHandler, framer == null ? new NativeFramer() : framer);
        }

        public ClientBuilder withMsgHandler(MsgHandler msgHandler) {
            this.msgHandler = msgHandler;
            return this;
        }

        public ClientBuilder withFramer(Framer framer) {
            this.framer = framer;
            return this;
        }

        public ClientBuilder withServerAddress(InetAddress serverAddress) {
            this.serverAddress = serverAddress;
            return this;
        }

        public ClientBuilder withPort(int port) {
            this.port = port;
            return this;
        }
    }


    // TODO - add UDPServer and UDPClient...
}
