package splumb.core.logging;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

class AsyncBus implements LogBus {
    @Override
    public void sub(Object listener) {
        bus.register(listener);
    }

    @Override
    public void pub(Object msg) {
        bus.post(msg);
    }

    private Executor eventThr = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .build());

    private AsyncEventBus bus = new AsyncEventBus(eventThr);
}