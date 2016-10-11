package com.aedan.jterminal.commandpackages.defaultpackage.math.commands;

import com.aedan.jterminal.commandpackages.defaultpackage.math.MathCommand;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 9/5/2016.
 * <p>
 * Default Command.
 */

public class AdditionCommand extends MathCommand {

    public AdditionCommand() {
        super("+");
        properties[0] = "Adds two numbers.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.size() != 3)
            throw new CommandHandler.CommandHandlerException(
                    "Wrong number of arguments given (given: 2, required: " + (args.size() - 1) + ")");

        try {
            if (args.get(1).getArgumentType().isSubset(ArgumentType.LONG)
                    && args.get(2).getArgumentType().isSubset(ArgumentType.LONG))
                output.println(apply(Long.parseLong(args.get(1).value), Long.parseLong(args.get(2).value)));
            else if (args.get(1).getArgumentType().isSubset(ArgumentType.DOUBLE)
                    && args.get(2).getArgumentType().isSubset(ArgumentType.DOUBLE))
                output.println(apply(Double.parseDouble(args.get(1).value), Double.parseDouble(args.get(2).value)));
            else
                output.println(args.get(1).value + args.get(2).value);
        } catch (NumberFormatException e) {
            throw new CommandHandler.CommandHandlerException(
                    "I" + e.getMessage().substring(5) + " is not a number");
        }
    }

    @Override
    public long apply(long l1, long l2) {
        return l1 + l2;
    }

    @Override
    public double apply(double d1, double d2) {
        return d1 + d2;
    }

}
