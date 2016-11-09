package com.aedan.jterminal.jterm.jtermcommandpackage;

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

    private JTermRuntime runtime;

    CallFunction(JTermRuntime runtime) {
        super("call", "Calls a function.");
        this.runtime = runtime;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        String[] arguments = new String[args.size() - 2];
        for (int i = 0; i < args.size() - 2; i++) {
            arguments[i] = args.get(i + 2).value;
        }

        Object o = runtime.getFunction(args.get(1).value).apply(arguments);
        if (o != null)
            output.print(o);
    }
}
