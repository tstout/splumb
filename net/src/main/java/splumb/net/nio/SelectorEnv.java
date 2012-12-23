package splumb.net.nio;

import com.google.common.eventbus.EventBus;
import splumb.common.logging.LogPublisher;
import splumb.net.framing.Framer;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;

/**
 * Context passed to socket state processing functions.
 */
class SelectorEnv {
    SelectableChannel socket;

    Map<SelectionKey, NetEndpoint> channelMap;

    Map<SelectableChannel, List<ByteBuffer>> pendingData;

    Map<SelectableChannel, MsgHandler> rspHandlers;

    int ops;

    Selector selector;

    NetEndpoint channel;

    ByteBuffer data;

    LogPublisher logger;

    SelectionKey key;

    NIOSelect nioSelector;

    EventBus bus;

    ByteBuffer readBuffer;

    NIOWorker worker;

    Framer framer;

    SocketChannel socketChannel() {
        return (SocketChannel) key.channel();
    }
}
