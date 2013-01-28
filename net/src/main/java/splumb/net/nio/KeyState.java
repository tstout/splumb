package splumb.net.nio;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Throwables.*;


enum KeyState {
    ACCEPTABLE(1) {
        @Override
        public boolean isActive(SelectionKey input) {
            return input.isAcceptable();
        }

        @Override
        void process(SelectorEnv env) {
            env.logger.info("Processing Accept");
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) env.key
                    .channel();

            SocketChannel socketChannel = null;

            try {
                socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
            } catch (Exception e) {
                throw propagate(e);
            }

            env.rspHandlers.put(socketChannel, env.rspHandlers.get(serverSocketChannel));

            //
            // TODO - consider using SelectionKey.attach() instead of separate channelMap...
            //
            SelectionKey newKey = null;
            try {
                newKey = socketChannel.register(env.selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                throw propagate(e);
            }

            env.channelMap.put(newKey, new InternalChannel(env.nioSelector, socketChannel, env.framer));

            env.logger.info("Server Accepted");
        }
    },

    CONNECTABLE(2) {
        @Override
        public boolean isActive(SelectionKey input) {
            return input.isConnectable();
        }

        @Override
        void process(SelectorEnv env) {
            SocketChannel socketChannel = env.socketChannel();

            try {
                if (socketChannel.finishConnect()) {
                    env.logger.info("finishConnect success for %s", env.key.channel());
                    env.bus.post(new SocketConnectedEvent(env.key.channel()));
                }
            } catch (IOException e) {
                env.key.cancel();
                return;
            }

            env.key.interestOps(env.key.interestOps() | SelectionKey.OP_READ);
        }
    },

//    INVALID(2) {
//        @Override
//        public boolean isActive(SelectionKey key) {
//            return !key.isValid();
//        }
//
//        @Override
//        void process(SelectorEnv env) {
//            //To change body of implemented methods use File | Settings | File Templates.
//        }
//    },

    READABLE(3) {
        @Override
        void process(SelectorEnv env) {
            SocketChannel socketChannel = env.socketChannel();

            //env.readBuffer.clear();

            int numRead;
            try {
                //numRead = socketChannel.read(env.readBuffer);
                numRead = socketChannel.read(env.framer.buffer());
            } catch (IOException e) {
                //
                // far end closed the connection, cancel the selection key and
                // close the channel.
                //
                env.key.cancel();

                try {
                    env.channelMap.remove(env.key);
                    socketChannel.close();
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }

                return;
            }

            if (numRead == -1) {
                //
                // Remote shutdown...close the connection.
                //
                try {
                    env.channelMap.remove(env.key);
                    env.key.channel().close();
                    env.logger.info("Read detected remote shutdown");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                env.key.cancel();
                return;
            }

            if (numRead == 0) {
                env.framer.buffer().clear();
                return;
            }

            if (env.framer.isFrameComplete(numRead)) {
                env.worker.processData(
                        (Client) env.channelMap.get(env.key), // TODO - is this cast necessary?
                        socketChannel,
                        env.framer.payload(), //.framer.buffer(),
                        env.framer.payload().remaining(),
                        env.rspHandlers.get(socketChannel));
            }
        }

        @Override
        public boolean isActive(SelectionKey key) {
            return key.isReadable();
        }
    },

    WRITABLE(4) {
        @Override
        public boolean isActive(SelectionKey key) {
            return key.isWritable();
        }

        @Override
        void process(SelectorEnv env) {
            env.logger.info("Processing write");
            SocketChannel socketChannel = env.socketChannel();

            List<ByteBuffer> queue = env.pendingData.get(socketChannel);

            //
            // Empty the transmit queue...
            //
            while (!queue.isEmpty()) {
                ByteBuffer buf = queue.get(0);
                try {
                    socketChannel.write(buf);
                } catch (IOException e) {
                    throw propagate(e);
                }

                if (buf.remaining() > 0) {
                    //
                    // socket buffer is full...
                    //
                    break;
                }
                queue.remove(0);
            }

            if (queue.isEmpty()) {
                //
                // All data transmitted, switch back to read...
                //
                env.key.interestOps(SelectionKey.OP_READ);
            }
        }
    };

    abstract void process(SelectorEnv env);

    abstract boolean isActive(SelectionKey key);

    KeyState(int order) {
        this.order = order;
    }

    private int order;

    private static final List<KeyState> stateCheckOrdering = new Ordering<KeyState>() {
        public int compare(KeyState left, KeyState right) {
            return Ints.compare(left.order, right.order);
        }
    }.sortedCopy(Arrays.asList(values()));

    static KeyState current(SelectionKey key) {
        for (KeyState state : stateCheckOrdering) {
            if (state.isActive(key)) {
                return state;
            }
        }

        throw new RuntimeException("Unknown KeyState");
    }
}
