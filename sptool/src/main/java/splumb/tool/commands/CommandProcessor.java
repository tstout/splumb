package splumb.tool.commands;

import com.google.common.base.Splitter;

import java.io.Writer;
import java.util.List;

import static com.google.common.base.Strings.*;
import static com.google.common.collect.Lists.*;

public class CommandProcessor {

    private static final NullTerminalCommand NULL_CMD = new NullTerminalCommand();
    private TerminalCommands terminalCommands = new TerminalCommands();
    private List<String> tokens;

    public void process(String lineFromTerminal, Writer writer) {
        TerminalCommand command = getCommand(lineFromTerminal);
        tokens.remove(0);

        if (command
                .exec(tokens, writer)
                .shouldTerminate()) {
            System.exit(0);
        }
    }

    private TerminalCommand getCommand(String lineFromTerminal) {

        List<String> tokensFromTerminal = newArrayList(Splitter
                .on(' ')
                .split(lineFromTerminal));
        tokens = tokensFromTerminal;

        return commandAvailable(tokensFromTerminal) ? terminalCommands.lookup(tokens.get(0)) : NULL_CMD;
    }

    private boolean commandAvailable(List<String> tokensFromTerminal) {
        return (tokensFromTerminal.size() > 0) && (!isNullOrEmpty(tokensFromTerminal.get(0)));
    }
}
