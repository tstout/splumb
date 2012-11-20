package splumb.net.nio;

class NetAddr implements Address {
    private int port;
    private String ip;

    public NetAddr(String ip, Integer port) {
        this.port = port;
        this.ip = ip;
    }

    public NetAddr(Integer port) {
        this.port = port;
        this.ip = "127.0.0.1";
    }

    @Override
    public String uniqueId() {
        return String.format("%s:%d", ip, port);
    }
}
