package splumb.tool.commands;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;

import java.io.Writer;

class UptimeCommand implements TerminalCommand<Integer> {

    @Override
    public OptionSpec<Integer> optSpec(OptionParser optionParser) {
        return optionParser.accepts("uptime").withOptionalArg().ofType(Integer.class);
    }

    @Override
    public TerminalCommand<Integer> exec(Integer arg, Writer writer) {
        return this;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
