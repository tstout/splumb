package splumb.net.nio;

//
// TODO - refactor this to use EventBus instead of listener interface...
//
public interface MsgHandler {
    void msgAvailable(NetEndpoint sender, byte[] msg);
}
