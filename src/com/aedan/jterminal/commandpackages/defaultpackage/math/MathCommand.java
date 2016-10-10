package com.aedan.jterminal.commandpackages.defaultpackage.math;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 9/6/2016.
 * <p>
 * Abstract class for Math Commands.
 */

public abstract class MathCommand extends Command {

    public MathCommand(String identifier) {
        super(identifier);
        properties[1] = identifier + " [num-1] [num-2]:\n" +
                "    Outputs [num-1] " + identifier + " [num-2].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.length() != 3)
            throw new CommandHandler.CommandHandlerException(
                    "Wrong number of arguments given (given: 2, required: " + (args.length() - 1) + ")");

        try {
            if (args.get(1).getArgumentType().isSubset(ArgumentType.LONG)
                    && args.get(2).getArgumentType().isSubset(ArgumentType.LONG))
                output.println(apply(Long.parseLong(args.get(1).value), Long.parseLong(args.get(2).value)));
            else
                output.println(apply(Double.parseDouble(args.get(1).value), Double.parseDouble(args.get(2).value)));
        } catch (NumberFormatException e) {
            throw new CommandHandler.CommandHandlerException(
                    "I" + e.getMessage().substring(5) + " is not a number");
        }
    }

    public abstract long apply(long l1, long l2);

    public abstract double apply(double d1, double d2);

}
