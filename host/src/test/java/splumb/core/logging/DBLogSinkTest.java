package splumb.core.logging;

import com.google.inject.Inject;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import splumb.common.logging.InfoLogEvent;
import splumb.core.db.DBTestModule;
import splumb.core.db.SplumbDB;
import splumb.common.test.GuiceJUnitRunner;
import splumb.common.test.GuiceJUnitRunner.GuiceModules;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ DBTestModule.class })
public class DBLogSinkTest {

    static final String TEST_MSG = "unit test msg";

    @Inject DBLogSink dbSink;
    @Inject SplumbDB db;

    @Before
    public void setup() {
        db.create();
    }

    @Test
    public void testInfoWrite() {
        dbSink.info(new InfoLogEvent("%s", new Object[]{TEST_MSG}));

        DBCommand cmd = db.createCommand();
        cmd.select(db.Log.LEVEL, db.Log.MSG);
        DBReader rdr = new DBReader();
        rdr.open(cmd, db.getConnection());

        //rdr.getRecordData(cmd, db.getConnection());

        List<LogRecord> records;

        try {
            records = rdr.getBeanList(LogRecord.class);
        } finally {
            rdr.close();
        }

        assertThat(records.size(), is(1));
        assertThat(records.get(0).getMsg(), is(TEST_MSG));
    }


}

