package splumb.net.nio;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Throwables.propagate;

enum SelectorOps {
    REGISTER {
        @Override
        public void process(SelectorEnv env) {
            try {
                //env.logger.info("SelectorOps: REGISTER");
                env.channelMap
                        .put(env.socket.register(env.selector, env.ops),
                                env.channel);
            } catch (ClosedChannelException e) {
                propagate(e);
            }
        }
    },

    CHANGEOPS {
        @Override
        void process(SelectorEnv env) {
            //env.logger.info("SelectorOps: CHANGEOPS");
            SelectionKey key =
                    env.socket.keyFor(env.selector);

            key.interestOps(env.ops);
        }

    },

    TRANSMIT {
        @Override
        void process(SelectorEnv env) {
            SelectionKey keyForWrite =
                    env.socket.keyFor(env.selector);

            List<ByteBuffer> queue = env.pendingData.get(env.socket);
            if (queue == null) {
                queue = new ArrayList<ByteBuffer>();
                env.pendingData.put(env.socket, queue);
            }

            queue.add(env.data);

            keyForWrite.interestOps(
                    keyForWrite.interestOps() | SelectionKey.OP_WRITE);
        }
    };

    abstract void process(SelectorEnv env);
}
