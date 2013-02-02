package splumb.tool.commands;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.google.common.base.Throwables.*;

class UnrecognizedCommand implements TerminalCommand {
    @Override
    public String command() {
        return "";  // TODO -  find suitable String.EMPTY;
    }

    @Override
    public TerminalCommand exec(List<String> args, Writer writer) {
        try {
            writer.write("Unrecognized command\n");
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
