package com.aedan.jterminal.jterm.jtermcommandpackage;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class IfEqual extends Command {

    IfEqual() {
        super("eq", "Returns true if two values are equal");
    }

    @Override
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        output.println(Objects.equals(args.get(1).value, args.get(2).value));
    }
}
