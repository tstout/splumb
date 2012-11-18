package splumb.net.nio;

import splumb.common.logging.LogPublisher;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.List;
import java.util.Map;

class SelectorEnv {
    public SelectableChannel socket;

    public Map<SelectionKey, NetEndpoint> channelMap;

    public Map<SelectableChannel, List<ByteBuffer>> pendingData;

    public int ops;

    public Selector selector;

    public NetEndpoint channel;

    public ByteBuffer data;

    public LogPublisher trace;
}
