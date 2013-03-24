package splumb.core.cli;

import com.google.common.collect.ImmutableMap;
import joptsimple.OptionParser;

import static com.google.common.collect.ImmutableMap.*;

class JmxAction implements OptAction {
    @Override
    public void execute(OptionParser parser, Object arg, Values.Builder valueBuilder) {
        // TODO -  this is a wash...these props must be specified at jvm startup to work...
        valueBuilder.withJmxPort((Integer) arg);

        ImmutableMap<String, String> props = new ImmutableMap.Builder<String, String>()
                .put("com.sun.management.jmxremote", "true")
                .put("com.sun.management.jmxremote.port", arg.toString())
                .put("com.sun.management.jmxremote.authenticate", "false")
                .put("com.sun.managementjmxremote.ssl", "false")
                .build();

        for (Entry<String, String> entry : props.entrySet()) {
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }
}
