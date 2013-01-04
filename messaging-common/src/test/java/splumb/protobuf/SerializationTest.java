package splumb.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;
import splumb.messaging.MapMsgBuilder;
import splumb.messaging.MapMessages;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.protobuf.BrokerMsg.*;

public class SerializationTest {

    @Test
    public void MapMsgEncodeTest() throws InvalidProtocolBufferException {
        Msg msg = new MapMsgBuilder()
                .addInt32("int-key", 1)
                .addString("string-key", "string-value")
                .build();

        byte[] serializedMsg = msg.toByteArray();

        Msg decodedMap = Msg.parseFrom(serializedMsg);
        MapMsg map = decodedMap.getMapMsg();
        assertThat(map.getItemsCount(), is(2));
        assertThat(MapMessages.getInt32(map, "int-key"), is(1));
        assertThat(MapMessages.getString(map, "string-key"), is("string-value"));
    }

}
