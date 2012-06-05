package splumb.core.cli;

import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

// TODO - need some docs here
public class OptCollection {
    public OptCollection add(OptAction... opts) {
        for (OptAction opt : opts) {
            add(opt);
        }

        return this;
    }

    public OptCollection add(OptAction opt) {
        opts.put(opt.getOption().getArgName(), opt);
        return this;
    }

    public void showDescr(String pgmName) {
        new HelpFormatter().printHelp(pgmName, commonOpts);
    }

    public boolean ArgExists(String argName) {
        return opts.containsKey(argName);
    }

    public String getArgValue(String argName) {
        return opts
                .containsKey(argName) ? opts.get(argName).getOption()
                .getValue() : "";
    }

    public void processOpts(String[] args) {
        for (Map.Entry<String, OptAction> opt : opts.entrySet()) {
            commonOpts.addOption(opt.getValue().getOption());
        }

        try {
            CommandLine cmd = new PosixParser().parse(commonOpts, args);

            for (Option opt : cmd.getOptions()) {
                OptAction action = opts.get(opt.getArgName());
                if (action != null) {
                    action.Run(opt.getValue(), this);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Map<String, OptAction> opts = new HashMap<String, OptAction>();
    private Options commonOpts = new Options();
}