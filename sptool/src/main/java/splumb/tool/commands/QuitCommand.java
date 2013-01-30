package splumb.tool.commands;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;

import java.io.Writer;

class QuitCommand implements TerminalCommand<Void> {
    @Override
    public OptionSpec<Void> optSpec(OptionParser optionParser) {
        return optionParser.accepts("quite").withOptionalArg().ofType(Void.class);
    }

    @Override
    public TerminalCommand<Void> exec(Void arg, Writer writer) {
        return this;
    }

    @Override
    public boolean shouldTerminate() {
        return true;
    }
}
