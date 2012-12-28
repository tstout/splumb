package splumb.common.logging;

public class ConsoleLogger implements LogPublisher {

    @Override
    public void info(String fmt, Object... parms) {
        System.out.printf(fmt, parms);
    }

    @Override
    public void info(String msg) {
        System.out.printf("%s\n", msg);
    }

    @Override
    public void error(String fmt, Object... parms) {
        System.out.printf(fmt, parms);
    }

    @Override
    public void debug(String fmt, Object... parms) {
        System.out.printf(fmt, parms);
    }
}
