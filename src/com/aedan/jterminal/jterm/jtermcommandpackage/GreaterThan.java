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

class GreaterThan extends Command {

    GreaterThan() {
        super("gt", "Returns true if a number is greater than another number.");
    }

    @Override
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment) throws CommandHandler.CommandHandlerException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        output.println(Double.parseDouble(args.get(1).value) > Double.parseDouble(args.get(2).value));
    }
}
