package splumb.tool;

import com.google.common.base.Splitter;
import jline.console.ConsoleReader;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import splumb.tool.commands.TerminalCommands;

import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.collect.Lists.*;

public class Main {


    public static void main(String[] args) {

        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("sptool>");

            OptionParser parser = new OptionParser();
            TerminalCommands terminalCommands = new TerminalCommands(parser);


            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            reader.setHistoryEnabled(true);

            while ((line = reader.readLine()) != null) {

                String[] cmd = newArrayList(Splitter.on(' ').split(line)).toArray(new String[]{});

                OptionSet optionSet = parser.parse(cmd);

                for (OptionSpec<?> optionSpec : optionSet.specs()) {
                    if (terminalCommands.lookup(optionSpec)
                            .exec(optionSet.valueOf(optionSpec), out)
                            .shouldTerminate()) {
                        System.exit(0);
                    }
                }

                out.flush();

            }

        } catch (IOException e) {

        }

    }
}
