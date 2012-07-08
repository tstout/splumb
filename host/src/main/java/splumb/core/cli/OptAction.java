package splumb.core.cli;

import org.apache.commons.cli.Option;

public interface OptAction {
    void Run(String arg, OptCollection registry);

    Option getOption();

    OptAction setOption(Option val);

}