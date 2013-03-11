package splumb.core.cli;

class Values implements OptValues {
    private final int jmxPort;
    private final boolean noDB;
    private final boolean dropTables;

    private Values(int jxmPort, boolean noDB, boolean dropTables) {
        this.jmxPort = jxmPort;
        this.noDB = noDB;
        this.dropTables = dropTables;
    }

    static Builder builder() {
        return new Builder();
    }

    @Override
    public int jmxPort() {
        return jmxPort;
    }

    @Override
    public boolean noDB() {
        return noDB;
    }

    @Override
    public boolean dropTables() {
        return dropTables;
    }

    static class Builder {
        private int jmxPort;
        private boolean noDB;
        private boolean dropTables;

        Builder withJmxPort(int port) {
            jmxPort = port;
            return this;
        }

        Builder withNoDB(boolean noDB) {
            this.noDB = noDB;
            return this;
        }

        Builder withDropTables(boolean dropTables) {
            this.dropTables = dropTables;
            return this;
        }

        Values build() {
            return new Values(jmxPort, noDB, dropTables);
        }
    }
}
