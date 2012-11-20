package splumb.net.nio;

//
// TODO - refactor this to use EventBus instead of listener interface... or
// at least make the sender a Client instead of NetEndpoint....
//
public interface MsgHandler {
    void msgAvailable(NetEndpoint sender, byte[] msg);
}
