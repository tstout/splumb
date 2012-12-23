package splumb.net.nio;

import com.google.common.base.Throwables;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;

import static com.google.common.collect.Queues.*;

class NIOWorker implements Runnable {
    private LinkedBlockingQueue<NIODataEvent> queue = newLinkedBlockingQueue();

    // TODO - is count relevant here?  I don't think so.
    public void processData(
            Client channel,
            SocketChannel socket,
            ByteBuffer data,
            int count,
            MsgHandler handler) {

        // TODO - review scatter/gather nio support...might make this copy moot.
        byte[] dataCopy = new byte[count];
        data.get(dataCopy, 0, count);
        //System.arraycopy(data, 0, dataCopy, 0, count);

        queue.add(new NIODataEvent(channel, socket, dataCopy, handler));
    }

    @Override
    public void run() {
        NIODataEvent dataEvent;

        for (;;) {
            try {
                dataEvent = queue.take();
                dataEvent
                        .handler
                        .msgAvailable(dataEvent.src, dataEvent.data);


            } catch (InterruptedException e) {
                Throwables.propagate(e);
            }
        }
    }
}
