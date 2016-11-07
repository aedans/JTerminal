package com.aedan.jterminal.bash.bashcommandpackage;

import com.aedan.jterminal.bash.BashRuntime;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

public class CallFunction extends Command {

    private BashRuntime bashRuntime;

    protected CallFunction(BashRuntime bashRuntime) {
        super("call", "Calls a function.");
        this.bashRuntime = bashRuntime;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        String[] arguments = new String[args.size() - 2];
        for (int i = 0; i < args.size() - 2; i++) {
            arguments[i] = args.get(i + 2).value;
        }

        output.print(bashRuntime.getFunctions().get(args.get(1).value).apply(arguments));
    }
}
