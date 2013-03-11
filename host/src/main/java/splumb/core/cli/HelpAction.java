package splumb.core.cli;

import joptsimple.OptionParser;

import java.io.IOException;

import static com.google.common.base.Throwables.*;

class HelpAction implements OptAction {
    @Override
    public void execute(OptionParser parser, Object arg, Values.Builder valuesBuilder) {
        try {
            parser.printHelpOn(System.out);
        } catch (IOException e) {
            throw propagate(e);
        }
    }
}
