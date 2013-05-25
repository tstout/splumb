package splumb.common.logging;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncLogBus implements LogBus {

    private Executor eventThr = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .build());

    private AsyncEventBus bus = new AsyncEventBus(eventThr);

    public AsyncLogBus() {
    }

    @Override
    public void sub(Object listener) {
        bus.register(listener);
    }

    @Override
    public void unsub(Object listener) {
        bus.unregister(listener);
    }

    @Override
    public void pub(LogEvent msg) {
        bus.post(msg);
    }
}