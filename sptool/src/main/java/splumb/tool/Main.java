package splumb.tool;

import com.google.inject.Guice;
import com.google.inject.Injector;
import jline.console.ConsoleReader;
import splumb.tool.commands.CommandProcessor;

import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.base.Throwables.*;

public class Main {


    public static void main(String[] args) {

        Injector injector = Guice.createInjector(
                new SpToolInjectModule());

        try {
            ConsoleReader reader = new ConsoleReader();
            reader.setPrompt("sptool>");

            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            reader.setHistoryEnabled(true);

            while ((line = reader.readLine()) != null) {
                new CommandProcessor().process(line, out);
                out.flush();
            }

        } catch (IOException e) {
            throw propagate(e);
        }

    }
}
