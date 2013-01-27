package splumb.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import splumb.messaging.MapMsgBuilder;
import splumb.messaging.MapMsgSelector;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.protobuf.BrokerMsg.*;

public class SerializationTest {

    @Test
    public void MapMsgEncodeTest() throws InvalidProtocolBufferException {
        Msg msg = new MapMsgBuilder()
                .add("int-key", 1)
                .withDestination("test.q")
                .add("string-key", "string-value")
                .build();

        byte[] serializedMsg = msg.toByteArray();

        Msg decodedMap = Msg.parseFrom(serializedMsg);
        MapMsg map = decodedMap.getMapMsg();

        MapMsgSelector mp = new MapMsgSelector(map);

        assertThat(map.getItemsCount(), is(2));
        assertThat(mp.get("int-key", Integer.class), is(1));
        assertThat(mp.get("string-key", String.class), is("string-value"));
    }

}
