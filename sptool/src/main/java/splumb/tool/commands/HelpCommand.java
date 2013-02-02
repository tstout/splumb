package splumb.tool.commands;

import java.io.Writer;
import java.util.List;

class HelpCommand implements TerminalCommand {
    private TerminalCommands terminalCommands;

    HelpCommand(TerminalCommands terminalCommands) {
        this.terminalCommands = terminalCommands;
    }

    @Override
    public String command() {
        return "help";
    }

    @Override
    public TerminalCommand exec(List<String> arg, Writer writer) {
        terminalCommands.printHelp(writer);
        return this;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
