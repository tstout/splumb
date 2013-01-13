package splumb.messaging;

import splumb.net.nio.Client;

public class QueueAvailableEvent {
    private final String destination;
    private final Client netConn;

    public QueueAvailableEvent(String destination, Client netConn) {
        this.netConn = netConn;
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public Client getNetConn() {
        return netConn;
    }
}
