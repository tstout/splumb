package splumb.msg;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static splumb.msg.SpHostMsg.*;

public class PingTest {

    @Test
    public void pingEncodeTest() {
        PingReq req = PingReq.newBuilder()
                .setAppName("testapp")
                .build();

        assertThat(req.toByteArray().length, not(0));
    }

    @Test
    public void pingDecodeTest() throws InvalidProtocolBufferException {
        PingResp resp = PingResp.newBuilder()
                .setStartTime("now")
                .build();

        PingResp parsedResp = PingResp.parseFrom(resp.toByteArray());

        assertThat(parsedResp.getStartTime(), is("now"));
    }
}
