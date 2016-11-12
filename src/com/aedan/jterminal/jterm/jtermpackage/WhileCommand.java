package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
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
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        StringOutput stringOutput = new StringOutput();
        while (!Objects.equals(stringOutput.getString().trim(), "false")) {
            environment.getCommandHandler().handleInput(input, stringOutput, args.get(1).value);
            environment.getCommandHandler().handleInput(input, output, args.get(2).value);
        }
    }
}
