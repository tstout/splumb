package splumb.net.nio;

import com.google.common.net.InetAddresses;

import java.net.InetAddress;

class NIOTestConstants {
    public static final int LOCAL_HOST_PORT = 8000;

    public static final long MSG_TIMEOUT_SECONDS = 4;

    public static final InetAddress LOCAL_HOST = InetAddresses.forString("127.0.0.1");
}
