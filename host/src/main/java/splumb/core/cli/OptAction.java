package splumb.core.cli;

import joptsimple.OptionParser;

interface OptAction {
    void execute(OptionParser parser, Object arg, Values.Builder valueBuilder);


}
