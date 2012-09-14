package splumb.common.logging;

public enum Level {
    DEBUG,
    INFO,
    ERROR;

    // TODO - replace with BiMap...

    public static Level fromInt(int val) {
        switch (val) {
            case 0:
                return DEBUG;
            case 1:
                return INFO;
            case 2:
                return ERROR;
            default:
                throw new RuntimeException(String.format("unknown log level %d", val));
        }
    }
}
