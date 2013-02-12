package splumb.messaging;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import splumb.net.nio.Client;
import splumb.net.nio.MsgHandler;
import splumb.net.nio.NetEndpoints;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;

class BootstrapConfig {
    private Client client;
    private NetEndpoints netEndpoints;
    private ListeningExecutorService executorService;

    @Inject
    BootstrapConfig(NetEndpoints netEndpoints, ExecutorService executor) {
        this.netEndpoints = netEndpoints;
        executorService = MoreExecutors.listeningDecorator(executor);
    }

//    List<ListBrokersResp> fetch(Inet4Address address, int port) {
//        Client client = netEndpoints.clientBuilder()
//                .withPort(8000)
//                .withServerAddress(NetConstants.LOCAL_HOST)
//                .withFramer(new NativeFramer())
//                .withMsgHandler( new Handler())
//                .build();
//
//        client.send();
//    }



    class Handler implements MsgHandler {

        @Override
        public void msgAvailable(Client sender, byte[] msg) {

        }
    }
}
