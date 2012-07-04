package splumb.core.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import splumb.core.db.DBTestModule;
import splumb.core.test.GuiceJUnitRunner;
import splumb.core.test.GuiceJUnitRunner.GuiceModules;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ DBTestModule.class })
public class DBLogSinkTest {

    @Test
    public void testSimpleWrite() {

    }

}
