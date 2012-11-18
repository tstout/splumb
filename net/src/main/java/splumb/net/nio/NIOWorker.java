package splumb.net.nio;

import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;

import static com.google.common.collect.Queues.*;

class NIOWorker implements Runnable {
    private LinkedBlockingQueue<NIODataEvent> queue = newLinkedBlockingQueue();


    public void processData(
            NetEndpoint channel,
            SocketChannel socket,
            byte[] data,
            int count,
            MsgHandler handler) {
        byte[] dataCopy = new byte[count];
        System.arraycopy(data, 0, dataCopy, 0, count);

        queue.add(new NIODataEvent(channel, socket, dataCopy, handler));
    }

    @Override
    public void run() {
        NIODataEvent dataEvent;

        for (; ; ) {
            try {
                dataEvent = queue.take();

                //bus.post(new MsgChannelDataEvent(dataEvent.src, dataEvent.data));

                dataEvent
                        .handler
                        .msgAvailable(dataEvent.src, dataEvent.data);


            } catch (InterruptedException e) {
                // TODO - research what should be done here...break maybe?
            }
        }
    }


    //private EventBus bus;

}
