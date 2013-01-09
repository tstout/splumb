package splumb.messaging.commands;

public enum BrokerCommands {
    ADD_QUEUE("addQueue"),
    ADD_TOPIC("addTopic");

    private String cmd;

    BrokerCommands(String cmd) {
        this.cmd = cmd;
    }

    public String strVal() {
        return this.cmd;
    }
}
