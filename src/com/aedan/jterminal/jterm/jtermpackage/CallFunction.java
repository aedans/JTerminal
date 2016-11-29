package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
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
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        Object[] arguments = new Object[args.size() - 2];
        for (int i = 0; i < args.size() - 2; i++) {
            arguments[i] = args.get(i + 2).toString();
        }

        return runtime.getFunction(args.get(1).toString()).apply(arguments);
    }
}
