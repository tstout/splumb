package splumb.tool.commands;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;

import java.io.IOException;
import java.io.Writer;

import static com.google.common.base.Throwables.propagate;

class UnrecognizedCommand implements TerminalCommand<Integer> {
    @Override
    public OptionSpec<Integer> optSpec(OptionParser optionParser) {
        return null;
    }

    @Override
    public TerminalCommand<Integer> exec(Object arg, Writer writer) {
        try {
            writer.write("Unrecognized command");
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
