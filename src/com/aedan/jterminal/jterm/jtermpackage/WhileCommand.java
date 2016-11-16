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

class WhileCommand extends Command {
    WhileCommand() {
        super("whl", "Executes a command until a command returns false.");
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class);

        StringOutput stringOutput = new StringOutput();
        while (!Objects.equals(stringOutput.getString().trim(), "false")) {
            environment.getCommandHandler().handleInput(args.get(1).toString(), input, stringOutput);
            environment.getCommandHandler().handleInput(args.get(2).toString(), input, output);
        }
    }
}
