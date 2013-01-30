package splumb.tool.commands;

import joptsimple.OptionParser;
import joptsimple.OptionSpec;

import java.io.Writer;

public interface TerminalCommand<T> {

    OptionSpec<T> optSpec(OptionParser optionParser);

    TerminalCommand<T> exec(T arg, Writer writer);

    boolean shouldTerminate();
}
