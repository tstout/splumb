package splumb.net.nio;

import com.google.common.net.InetAddresses;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.google.common.base.Throwables.propagate;

public final class NetConstants {
    public static final InetAddress LOCAL_HOST = InetAddresses.forString("127.0.0.1");

    public static String HOST_NAME;

    // TODO - get rid of this static block....
    static {
        try {
            HOST_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            propagate(e);
        }
    }
}
