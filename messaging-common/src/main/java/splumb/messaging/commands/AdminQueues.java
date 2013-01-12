package splumb.messaging.commands;


public enum AdminQueues {
    ADMIN_REQ_Q,
    ADMIN_RESP_Q,
    DEAD_LETTER_Q;

    public String qName() {
        return String.format("SPLUMB_%s", this.name());
    }
}
