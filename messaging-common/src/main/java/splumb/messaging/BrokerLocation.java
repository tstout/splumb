package splumb.messaging;

class BrokerLocation {
    private final String host;
    private final int port;

    BrokerLocation(String host, int port) {
        this.host = host;
        this.port = port;
    }

    String host() {
        return host;
    }

    int port() {
        return port;
    }
}
