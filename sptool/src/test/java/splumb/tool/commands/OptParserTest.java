package splumb.tool.commands;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.junit.Test;

import java.io.StringWriter;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class OptParserTest {
    @Test
    public void evaluateOptionSpec() {

        OptionParser parser = new OptionParser();


        OptionSpec<Integer> spec = parser.accepts("help").withOptionalArg().ofType(Integer.class);

        OptionSet set = parser.parse("-help");

        assertThat(set.has(spec), is(true));

    }

    @Test
    public void execCommandProcessor() {

        CommandProcessor processor = new CommandProcessor();
        StringWriter writer = new StringWriter();

        processor.process("help", writer);

        assertThat(writer.toString().length(), is(not(0)));


    }
}
