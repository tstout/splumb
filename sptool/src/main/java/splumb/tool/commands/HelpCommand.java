package splumb.tool.commands;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;

import java.io.IOException;
import java.io.Writer;

import static com.google.common.base.Throwables.*;

class HelpCommand implements TerminalCommand<Void> {
    private OptionParser optionParser;

    @Override
    public OptionSpec<Void> optSpec(OptionParser optionParser) {
        this.optionParser = optionParser;
        return optionParser.accepts("help").withOptionalArg().ofType(Void.class);
    }

    @Override
    public TerminalCommand<Void> exec(Void arg, Writer writer) {
        try {
            optionParser.printHelpOn(writer);
        } catch (IOException e) {
            throw propagate(e);
        }
        return this;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
