package splumb.core.cli;

import joptsimple.OptionParser;

class JmxAction implements OptAction {
    @Override
    public void execute(OptionParser parser, Object arg, Values.Builder valueBuilder) {
       valueBuilder.withJmxPort(Integer.class.cast(arg));
    }
}
