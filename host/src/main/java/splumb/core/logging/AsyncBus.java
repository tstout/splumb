package splumb.core.logging;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class AsyncBus implements LogBus {

    private Executor eventThr = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder()
                    .setDaemon(true)
                    .build());

    private AsyncEventBus bus = new AsyncEventBus(eventThr);

    // TODO - inject a log configuration .....
    public AsyncBus() {
    }


    @Override
    public void sub(Object listener) {
        bus.register(listener);
    }

    @Override
    public void pub(LogEvent msg) {
        bus.post(msg);
    }
}