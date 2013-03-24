package splumb.core.cli;

import joptsimple.OptionParser;

class DroptablesAction implements OptAction {
    @Override
    public void execute(OptionParser parser, Object arg, Values.Builder valueBuilder) {
        valueBuilder.withDropTables(true);
    }
}
