package com.aedan.jterminal.jterm.jtermcommandpackage;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

public class LessThan extends Command {

    protected LessThan() {
        super("lt", "Returns true if a number is less than another number.");
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output) throws CommandHandler.CommandHandlerException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        output.println(Double.parseDouble(args.get(1).value) < Double.parseDouble(args.get(2).value));
    }
}
