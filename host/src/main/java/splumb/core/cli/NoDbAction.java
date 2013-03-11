package splumb.core.cli;

import joptsimple.OptionParser;

class NoDbAction implements OptAction {
    @Override
    public void execute(OptionParser parser, Object arg, Values.Builder valueBuilder) {
        valueBuilder.withNoDB(true);
    }
}
