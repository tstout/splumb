package splumb.tool.commands;

import java.io.Writer;
import java.util.List;

class UptimeCommand implements TerminalCommand {

    @Override
    public String command() {
        return "uptime";
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
