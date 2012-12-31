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
        MapMsg msg = new MapMsgBuilder()
                .addInt32("int-key", 1)
                .addString("string-key", "string-value")
                .build();

        byte[] serializedMsg = msg.toByteArray();

        MapMsg decodedMap = MapMsg.parseFrom(serializedMsg);

        assertThat(decodedMap.getItemsCount(), is(2));
        assertThat(MapMessages.getInt32(decodedMap, "int-key"), is(1));
        assertThat(MapMessages.getString(decodedMap, "string-key"), is("string-value"));
    }

}
