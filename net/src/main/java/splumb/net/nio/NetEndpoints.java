package splumb.net.nio;

import splumb.common.logging.LogPublisher;

import java.net.InetAddress;

public class NetEndpoints {

    private LogPublisher logger;
    private NIOSelect selector;

    public NetEndpoints(LogPublisher logger) {
        this.logger = logger;
        selector = new NIOSelect(logger);
    }


    public Server newTCPServer(MsgHandler handler) {
        return new TCPServer(selector, handler);
    }

    public Client newTCPClient(InetAddress serverAddress, int port, MsgHandler handler) {
        return new TCPClient(serverAddress, port, handler, selector);
    }
}
