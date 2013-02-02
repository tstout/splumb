package splumb.tool.commands;

import java.io.Writer;
import java.util.List;

public interface TerminalCommand {
    String command();

    TerminalCommand exec(List<String> args, Writer writer);

    // TODO - get rid of this method...
    boolean shouldTerminate();
}
