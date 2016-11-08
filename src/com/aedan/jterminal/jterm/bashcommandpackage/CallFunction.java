package com.aedan.jterminal.jterm.bashcommandpackage;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

class CallFunction extends Command {

    private JTermRuntime bashRuntime;

    CallFunction(JTermRuntime bashRuntime) {
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

        Object o = bashRuntime.getFunctions().get(args.get(1).value).apply(arguments);
        if (o != null)
            output.print(o);
    }
}
