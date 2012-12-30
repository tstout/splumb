package splumb.messaging;

class Sink implements MessageSink {
    private String destination;

    public Sink(String destination) {
        this.destination = destination;
    }

    @Override
    public void receive(Message message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
