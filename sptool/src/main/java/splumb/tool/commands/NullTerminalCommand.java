package splumb.tool.commands;

import java.io.Writer;
import java.util.List;

class NullTerminalCommand implements TerminalCommand {
    @Override
    public String command() {
        return "";
    }

    @Override
    public TerminalCommand exec(List<String> args, Writer writer) {
        return this;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
