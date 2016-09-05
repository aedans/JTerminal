package com.aedan.jterminal.commands.defaultpackage.math.commands;

import com.aedan.jterminal.Directory;
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

public class AdditionCommand extends Command {

    public AdditionCommand() {
        super("+");
        properties[0] = "Adds two numbers.";
        properties[1] = "+ [double-1] [double-2]: Outputs [double-1] + [double-2].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.INTEGER, ArgumentType.INTEGER);

        output.println(Integer.parseInt(args.get(1).value) + Integer.parseInt(args.get(2).value));
    }
}
