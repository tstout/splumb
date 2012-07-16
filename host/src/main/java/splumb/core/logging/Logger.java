package splumb.core.logging;

import com.google.inject.Inject;
import splumb.common.logging.LogPublisher;

//
// TODO - need to add some filtering capabilities here
//
class Logger implements LogPublisher {
    @Inject
    Logger(LogBus bus) {
        this.bus = bus;
    }

    @Override
    public void info(String fmt, Object... parms) {
        bus.pub(new InfoLogEvent(fmt, parms));
    }

    @Override
    public void info(String msg) {
        bus.pub(new InfoLogEvent("%s", new Object[]{msg}));
    }

    @Override
    public void error(String fmt, Object... parms) {
        bus.pub(new ErrorLogEvent(fmt, parms));
    }

    @Override
    public void debug(String fmt, Object... parms) {
        bus.pub(new DebugLogEvent(fmt, parms));
    }

    private LogBus bus;
}
