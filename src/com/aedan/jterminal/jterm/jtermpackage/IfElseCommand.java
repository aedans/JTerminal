package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.StringOutput;

import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class IfElseCommand extends Command {
    IfElseCommand() {
        super("elif", "Executes a command if a command returns true, otherwise executes the second command.");
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class, String.class);

        StringOutput stringOutput = new StringOutput();
        environment.getCommandHandler().handleInput(args.get(1).toString(), input, stringOutput);

        if (Objects.equals(stringOutput.getString().trim(), "true")) {
            environment.getCommandHandler().handleInput(args.get(2).toString(), input, output);
        } else {
            environment.getCommandHandler().handleInput(args.get(3).toString(), input, output);
        }
    }
}
