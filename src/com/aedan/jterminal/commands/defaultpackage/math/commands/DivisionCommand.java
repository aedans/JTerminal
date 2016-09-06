package com.aedan.jterminal.commands.defaultpackage.math.commands;

import com.aedan.jterminal.environment.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 9/5/2016.
 *
 * Default Command.
 */

public class DivisionCommand extends Command {

    public DivisionCommand() {
        super("/");
        properties[0] = "Divides two numbers.";
        properties[1] =
                "/ [double-1] [double-2]:\n" +
                "    Outputs [double-1] / [double-2].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.DOUBLE, ArgumentType.DOUBLE);

        output.println(Double.parseDouble(args.get(1).value) / Double.parseDouble(args.get(2).value));
    }
}
