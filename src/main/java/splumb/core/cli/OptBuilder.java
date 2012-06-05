package splumb.core.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

public class OptBuilder {
    public OptBuilder withArgName(String name) {
        OptionBuilder.withArgName(name);
        return this;
    }

    public OptBuilder hasArg() {
        OptionBuilder.hasArg();
        return this;
    }

    public OptBuilder withDescription(String descr) {
        OptionBuilder.withDescription(descr);
        return this;
    }

    public OptBuilder isRequired() {
        OptionBuilder.isRequired();
        return this;
    }

    public Option create(String name) {
        return OptionBuilder.create(name);
    }
}
