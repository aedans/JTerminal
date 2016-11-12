package com.aedan.jterminal.packages.defaultpackage.math;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
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
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        try {
            if (args.matches(ArgumentType.LONG, ArgumentType.LONG) == MatchResult.CORRECT_ARGS)
                output.println(apply(Long.parseLong(args.get(1).value), Long.parseLong(args.get(2).value)));
            else if (args.matches(ArgumentType.DOUBLE, ArgumentType.DOUBLE) == MatchResult.CORRECT_ARGS)
                output.println(apply(Double.parseDouble(args.get(1).value), Double.parseDouble(args.get(2).value)));
            else
                throw new JTerminalException("Incorrect arguments given", this);
        } catch (NumberFormatException e) {
            throw new JTerminalException("I" + e.getMessage().substring(5) + " is not a number", this);
        }
    }

    public abstract long apply(long l1, long l2);

    public abstract double apply(double d1, double d2);
}
