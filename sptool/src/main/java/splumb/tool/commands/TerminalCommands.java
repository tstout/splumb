package splumb.tool.commands;

import com.google.common.collect.ImmutableSet;

public class TerminalCommands {
    public static ImmutableSet<? extends TerminalCommand<?>> commandSet() {
        return ImmutableSet.of(
                new HelpCommand(),
                new UptimeCommand());
    }
}
