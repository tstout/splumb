package splumb.core.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import splumb.common.logging.Level;
import splumb.common.logging.LogConfig;
import splumb.common.test.GuiceJUnitRunner;
import splumb.core.db.DBTestModule;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ DevLoggingModule.class, DBTestModule.class})
public class LogConfigTest {

    @Inject
    LogConfig logConfig;

    @Inject
    HostLogger logger;

    @Test
    public void testDebugSetting() {
        assertTrue(logConfig.getLevel(HostLogger.LOGGER_NAME).compareTo(Level.DEBUG) == 0);
    }

    @Test
    public void testDebugTrace() {
        assertTrue(logger.isRouteable(Level.DEBUG));
    }
}
