package splumb.messaging.commands;


public enum AdminQueues {
    ADMIN_REQ_Q,
    ADMIN_RESP_Q,
    DEAD_LETTER_Q;

    private String qName;

    AdminQueues() {
        qName = String.format("splumb.%s", this.name().replace('_', '.').toLowerCase());
    }

    public String qName() {
        return qName;
    }
}
