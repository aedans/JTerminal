package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.StringOutput;

import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class IfCommand extends Command {
    IfCommand() {
        super("if", "Executes a command if a command returns true.");
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        StringOutput stringOutput = new StringOutput();
        environment.getCommandHandler().handleInput(args.get(1).toString(), input, stringOutput);

        if (Objects.equals(stringOutput.getString().trim(), "true")) {
            environment.getCommandHandler().handleInput(args.get(2).toString(), input, output);
        }
    }
}
