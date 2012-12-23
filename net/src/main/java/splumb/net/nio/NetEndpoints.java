package splumb.net.nio;

import com.google.common.eventbus.EventBus;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.Framer;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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

    public Server newTCPServer(MsgHandler handler, Framer framer) {
        return new TCPServer(selector, handler, framer);
    }

    public Client newTCPClient(InetAddress serverAddress, int port, MsgHandler handler, Framer framer) {
        TCPClient client = new TCPClient(serverAddress, port, handler, selector, scheduler, framer);
        bus.register(client);
        return client;
    }

    // TODO - add UDPServer and UDPClient...
}
