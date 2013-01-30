package splumb.tool;

import jline.console.ConsoleReader;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import splumb.tool.commands.TerminalCommand;
import splumb.tool.commands.TerminalCommands;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import static com.google.common.collect.Sets.*;

public class Main {


    public static void main(String[] args) {

        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("sptool>");

            OptionParser parser = new OptionParser();

            Set<OptionSpec<?>> commands = newHashSet();

            for (TerminalCommand<?> command : TerminalCommands.commandSet()) {
                commands.add(command.optSpec(parser));
            }

            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            reader.setHistoryEnabled(true);

            while ((line = reader.readLine()) != null) {

                OptionSet optionSet = parser.parse(line);
                for (OptionSpec<?> optionSpec : commands) {
                    if (optionSet.has(optionSpec)) {
                        // need a map of optionSpec to TerminalCommands...
                        // do a lookup, then exec and check if terminate set
                        //
                    }
                }

                out.flush();

                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
            }

        } catch (IOException e) {

        }

    }
}
