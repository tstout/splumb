package splumb.tool.commands;

import com.google.common.collect.ImmutableSet;
import joptsimple.OptionParser;
import joptsimple.OptionSpec;

import java.util.Map;

import static com.google.common.base.Functions.*;
import static com.google.common.collect.Maps.*;

public class TerminalCommands {

    private ImmutableSet<? extends TerminalCommand<?>> commands =
            new ImmutableSet.Builder()
            .add(new HelpCommand())
            .add(new UptimeCommand())
            .add(new QuitCommand())
            .build();

    private Map<OptionSpec<?>, TerminalCommand<?>> commandMap = newHashMap();

    public TerminalCommands(OptionParser parser) {

        for (TerminalCommand<?> command : commands) {
            commandMap.put(command.optSpec(parser), command);
        }
    }

    public TerminalCommand<?> lookup(OptionSpec<?> optionSpec) {
        return forMap(commandMap, new UnrecognizedCommand()).apply(optionSpec);
    }

}
