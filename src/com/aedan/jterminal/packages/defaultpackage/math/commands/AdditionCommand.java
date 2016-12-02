package com.aedan.jterminal.packages.defaultpackage.math.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
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

    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.size() != 3)
            throw new JTerminalException(
                    "Wrong number of arguments given (given: " + (args.size() - 1) + ", required 2)", this);

        if (args.matches(Number.class, Number.class) == MatchResult.CORRECT_ARGS) {
            return apply(((Number) args.get(1).get()).doubleValue(), ((Number) args.get(2).get()).doubleValue());
        } else {
            try {
                return args.get(1).get().toString() + args.get(2).get().toString();
            } catch (NumberFormatException e) {
                throw new JTerminalException("I" + e.getMessage().substring(5) + " is not a number", this);
            }
        }
    }

    @Override
    public double apply(double d1, double d2) {
        return d1 + d2;
    }
}