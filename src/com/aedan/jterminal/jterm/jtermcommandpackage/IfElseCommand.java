package com.aedan.jterminal.jterm.jtermcommandpackage;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
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
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING, ArgumentType.STRING);

        StringOutput stringOutput = new StringOutput();
        environment.getCommandHandler().handleInput(input, stringOutput, args.get(1).value);

        if (Objects.equals(stringOutput.getString().trim(), "true")) {
            environment.getCommandHandler().handleInput(input, output, args.get(2).value);
        } else {
            environment.getCommandHandler().handleInput(input, output, args.get(3).value);

        }
    }
}
