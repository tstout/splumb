package splumb.tool.commands;

import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static com.google.common.base.Functions.*;
import static com.google.common.base.Throwables.*;
import static com.google.common.collect.Maps.*;

class TerminalCommands {
    private ImmutableSet<TerminalCommand> commands =
            new ImmutableSet.Builder<TerminalCommand>()
                    .add(new HelpCommand(this))
                    .add(new UptimeCommand())
                    .add(new QuitCommand())
                    .build();

    private Map<String, TerminalCommand> commandMap = newHashMap();

    TerminalCommands() {
        for (TerminalCommand command : commands) {
            commandMap.put(command.command(), command);
        }
    }

    TerminalCommand lookup(String cmdName) {
        return forMap(commandMap, new UnrecognizedCommand()).apply(cmdName);
    }

    void printHelp(Writer writer) {
        for (TerminalCommand command : commands) {
            try {
                writer.write(String.format("%s\n", command.command()));
            } catch (IOException e) {
                throw propagate(e);
            }
        }
    }
}
