package splumb.net.nio;

/**
 *
 */
class DataAvailableEvent {
    private final byte[] data;
    private final Client source;

    public DataAvailableEvent(byte[] data, Client source) {
        this.data = data;
        this.source = source;
    }

    public byte[] getData() {
        return data;
    }

    public Client getSource() {
        return source;
    }
}
