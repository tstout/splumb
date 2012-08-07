package splumb.core.logging;

import com.google.inject.Inject;
import splumb.common.logging.*;

//
// TODO - need to add some filtering capabilities here
// the configuration needs to be injected here, not the
// async bus for perf reasons.
//
class Logger implements LogPublisher {

    private LogBus bus;

    enum Level {
        ERROR,
        INFO,
        DEBUG
    };

    @Inject
    Logger(LogBus bus) {
        this.bus = bus;
    }

    @Override
    public void info(String fmt, Object... parms) {
        if (isRouteable(Level.INFO)) {
            bus.pub(new InfoLogEvent(fmt, parms));
        }
    }

    @Override
    public void info(String msg) {
        if (isRouteable(Level.INFO)) {
            bus.pub(new InfoLogEvent("%s", new Object[]{msg}));
        }
    }

    @Override
    public void error(String fmt, Object... parms) {
        if (isRouteable(Level.ERROR)) {
            bus.pub(new ErrorLogEvent(fmt, parms));
        }
    }

    @Override
    public void debug(String fmt, Object... parms) {
        if (isRouteable(Level.DEBUG)) {
            bus.pub(new DebugLogEvent(fmt, parms));
        }
    }

    private boolean isRouteable(Level logLevel) {
        return true;
    }

}
