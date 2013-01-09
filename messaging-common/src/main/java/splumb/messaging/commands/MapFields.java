package splumb.messaging.commands;

public enum MapFields {
    /**
     *
     */
    COMMAND("BrokerCmd"),
    /**
     * Queue or topic name
     */
    DESTINATION("DestName");

    private String value;

    MapFields(String value) {
        this.value = value;
    }

    String strVal() {
        return value;
    }
}
