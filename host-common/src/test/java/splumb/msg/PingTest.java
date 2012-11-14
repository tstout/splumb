package splumb.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PingTest {

    @Test
    public void pingEncodeTest() {
        SpHostMsg.PingReq req = SpHostMsg.PingReq.newBuilder()
                .setAppName("testapp")
                .build();

        assertThat(req.toByteArray().length, not(0));
    }

    @Test
    public void pingDecodeTest() throws InvalidProtocolBufferException {
        SpHostMsg.PingResp resp = SpHostMsg.PingResp.newBuilder()
                .setStartTime("now")
                .build();

        SpHostMsg.PingResp parsedResp = SpHostMsg.PingResp.parseFrom(resp.toByteArray());

        assertThat(parsedResp.getStartTime(), is("now"));
    }
}
