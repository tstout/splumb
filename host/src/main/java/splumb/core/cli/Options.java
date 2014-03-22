package splumb.core.cli;

import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.*;
import static com.google.common.collect.Maps.*;
import static java.util.Arrays.*;
import static splumb.core.cli.Options.Args.*;

class Options {
    private OptionSet optionSet;
    private final Map<Args, OptAction> actions = newHashMap();
    private final OptionParser parser;

    private final static OptAction NULL_ACTION = new OptAction() {
        @Override
        public void execute(OptionParser parser, Object arg, Values.Builder builder) {
        }
    };

    Options() {
        parser = new OptionParser();
        parser.acceptsAll(NO_DB.aliases, "Run without DB");
        parser.acceptsAll(HELP.aliases, "Show help");
        parser.acceptsAll(VERBOSE.aliases, "Dump log to stdout");

        parser.acceptsAll(JMX.aliases, "Enable JMX connectivity")
                .withOptionalArg()
                .ofType(Integer.class)
                .defaultsTo(8004);
    }

    Options register(Args arg, OptAction action) {
        actions.put(arg, action);
        return this;
    }

    void process(String[] args, Values.Builder valueBuilder) {
        optionSet = parser.parse(args);

        for (Args arg : Args.values()) {
            if (argPresent(arg)) {
                Functions.forMap(actions, NULL_ACTION)
                        .apply(arg)
                        .execute(parser, optionSet.valueOf(arg.name().toLowerCase()), valueBuilder);
            }
        }
    }

    private boolean argPresent(Args arg) {
        return from(arg.aliases)
                .filter(Fn.argExists(optionSet))
                .size() != 0;
    }

    enum Args {
        NO_DB("nodb"),
        DROP_TABLES("droptables"),
        HELP("help", "h"),
        VERBOSE("verbose"),
        JMX("jmx");
        private List<String> aliases;

        Args(String... aliases) {
            this.aliases = asList(aliases);
        }
    }

    static class Fn {
        static Predicate<String> argExists(final OptionSet set) {
            return new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return set.has(input);
                }
            };
        }
    }
}
