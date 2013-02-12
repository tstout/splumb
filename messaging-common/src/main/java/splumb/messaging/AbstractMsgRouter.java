package splumb.messaging;

import splumb.net.nio.MsgHandler;

public abstract class AbstractMsgRouter<M, T> implements MsgHandler {

//    @Override
//    public void msgAvailable(Client sender, byte[] msg) {
//        try {
//                M parsedMsg = parseMsg(msg);
//
//            eventBus()
//                    .post(msgSelector()
//                    .fromType(adminMsg.getType())
//                    .select(adminMsg));
//
//        } catch (InvalidProtocolBufferException e) {
//            throw propagate(e);
//        }
//    }
//
//    abstract protected EventBus eventBus();
//
//    abstract protected MsgSelector<M, T> msgSelector();
//
//    abstract protected M parseMsg(byte[] msg);
//
//    abstract protected T



}
