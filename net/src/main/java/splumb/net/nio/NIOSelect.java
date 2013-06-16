package splumb.net.nio;

import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import splumb.common.logging.LogPublisher;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Maps.*;


// TODO - this class is still too busy...way too many private fields here.
class NIOSelect implements Runnable {

    private final ConcurrentLinkedQueue<SelectorCmd> pendingChanges =
            new ConcurrentLinkedQueue<SelectorCmd>();

    private Map<SelectableChannel, MsgHandler> rspHandlers = newConcurrentMap();
    private Map<SelectableChannel, List<ByteBuffer>> pendingData =  newConcurrentMap();
    private Map<SelectionKey, NetEndpoint> channelMap = newConcurrentMap();
    private NIOWorker worker;
    private Selector selector;
    private LogPublisher logger;
    private EventBus bus;

    NIOSelect(LogPublisher logger, EventBus bus) {
        this.logger = logger;
        this.bus = bus;
        worker = new NIOWorker();

        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw propagate(e);
        }

        Executor selectThr = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .build());

        Executor workerThr = Executors.newSingleThreadExecutor(
                new ThreadFactoryBuilder()
                        .setDaemon(true)
                        .build());

        selectThr.execute(this);
        workerThr.execute(worker);
    }

    public void applyChange(SelectorCmd cmd) {
        logger.info("applyChange");

        if (cmd.handler != null) {
            rspHandlers.put(cmd.socket, cmd.handler);
        }

        synchronized (pendingChanges) {
            pendingChanges.add(cmd);
        }

        selector.wakeup();
    }

    @Override
    public void run() {
        SelectorEnv env = new SelectorEnv();
        env.selector = selector;
        env.channelMap = channelMap;
        env.pendingData = pendingData;
        env.rspHandlers = rspHandlers;
        env.nioSelector = this;
        env.bus = bus;
        env.logger = logger;
        env.worker = worker;

        for (;;) {
            try {
                synchronized (pendingChanges) {
                    for (SelectorCmd change : pendingChanges) {
                        env.channel = change.channel;
                        env.socket = change.socket;
                        env.ops = change.ops;
                        env.data = change.data;
                        env.framer = change.framer;

                        change.type.process(env);
                    }

                    pendingChanges.clear();
                }

                selector.select();

                // TODO - how about making this a for loop..I like the for loop style better...
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    env.key = key;

                    if (!key.isValid()) {
                        logger.error("Invalid key");
                        continue;
                    }

                   //
                   // process the selection key according to its current state...
                    //
                   KeyState.current(key).process(env);
                }
            } catch (Exception e) {
                throw propagate(e);
            }
        }
    }
}
