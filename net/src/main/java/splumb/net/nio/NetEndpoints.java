package splumb.net.nio;

import com.google.common.eventbus.EventBus;
import splumb.common.logging.LogPublisher;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Provides factories for creating non-blocking network connections.
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

    public Server newTCPServer(MsgHandler handler) {
        return new TCPServer(selector, handler);
    }

    public Client newTCPClient(InetAddress serverAddress, int port, MsgHandler handler) {
        TCPClient client = new TCPClient(serverAddress, port, handler, selector, scheduler);
        bus.register(client);
        return client;
    }
}
