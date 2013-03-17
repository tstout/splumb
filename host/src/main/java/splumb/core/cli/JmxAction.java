package splumb.core.cli;

import joptsimple.OptionParser;

import java.util.Properties;

import static com.google.common.collect.ImmutableMap.*;

class JmxAction implements OptAction {
    @Override
    public void execute(OptionParser parser, Object arg, Values.Builder valueBuilder) {
        valueBuilder.withJmxPort((Integer) arg);

        Properties jmxProps = new Properties();
        jmxProps.putAll(of(
                "com.sun.management.jmxremote", true,
                "com.sun.management.jmxremote.port", (Integer)arg,
                "com.sun.management.jmxremote.authenticate", false,
                "com.sun.managementjmxremote.ssl", false));




        //System.setProperties(jmxProps);
    }
}
