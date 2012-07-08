package splumb.core.cli;

import org.apache.commons.cli.Option;

public abstract class AbstractOptAction implements OptAction {
    @Override
    public abstract void Run(String arg, OptCollection registry);

    @Override
    public Option getOption() {
        return _opt;
    }

    @Override
    public OptAction setOption(Option val) {
        _opt = val;
        return this;
    }

    private Option _opt;
}
