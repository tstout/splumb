package splumb.messaging;

/**
 * Maps a protobuf type enum to a protobuf message class.
 * TODO - add .proto example to clarify. Might also explain the
 * Object in the signature for the purposes of Guava's EventBus.
 * @param <T>
 */
public interface MsgSelector<T> {
    Object select(T msg);
}