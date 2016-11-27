package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

class IfElseCommand extends Command {
    IfElseCommand() {
        super("elif", "Executes a command if a command returns true, otherwise executes the second command.");
    }

    @Override
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class, String.class);

        if ((boolean) environment.getCommandHandler().handleInput(args.get(1).toString(), input, output)) {
            return environment.getCommandHandler().handleInput(args.get(2).toString(), input, output);
        } else {
            return environment.getCommandHandler().handleInput(args.get(3).toString(), input, output);
        }
    }
}
