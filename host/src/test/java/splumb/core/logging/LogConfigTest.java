package splumb.core.logging;

import org.junit.Test;
import org.junit.runner.RunWith;
import splumb.common.logging.LogConfig;
import splumb.common.test.GuiceJUnitRunner;
import splumb.core.db.DBTestModule;

import javax.inject.Inject;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ DevLoggingModule.class, DBTestModule.class})
public class LogConfigTest {

    @Inject
    LogConfig logConfig;

    @Inject
    HostLogger logger;

    @Test
    public void testDebugSetting() {
        //assertThat(logConfig.getLevel(HostLogger.LOGGER_NAME).compareTo(Level.DEBUG), is(0));
    }

    @Test
    public void testDebugTrace() {
        //assertThat(logger.isRouteable(Level.DEBUG), is(true));
    }
}
