package com.aedan.jterminal.packages.defaultpackage.math.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.math.MathCommand;

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
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.size() != 3)
            throw new JTerminalException(
                    "Wrong number of arguments given (given: " + (args.size() - 1) + ", required 2)", this);

        if (args.get(1).getArgumentType().isSubset(ArgumentType.LONG)
                && args.get(2).getArgumentType().isSubset(ArgumentType.LONG))
            output.println(apply(Long.parseLong(args.get(1).toString()), Long.parseLong(args.get(2).toString())));
        else if (args.get(1).getArgumentType().isSubset(ArgumentType.DOUBLE)
                && args.get(2).getArgumentType().isSubset(ArgumentType.DOUBLE))
            output.println(apply(Double.parseDouble(args.get(1).toString()), Double.parseDouble(args.get(2).toString())));
        else
            try {
                output.println(args.get(1).toString() + args.get(2).toString());
            } catch (NumberFormatException e) {
                throw new JTerminalException("I" + e.getMessage().substring(5) + " is not a number", this);
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
