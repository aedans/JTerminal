package com.aedan.jterminal.packages.defaultpackage.math;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.Environment;
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
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        try {
            if (args.matches(Number.class, Number.class) == MatchResult.CORRECT_ARGS) {
                return apply(((Number) args.get(1).get()).doubleValue(), ((Number) args.get(2).get()).doubleValue());
            } else {
                throw new JTerminalException("Incorrect arguments given", this);
            }
        } catch (NumberFormatException e) {
            throw new JTerminalException("I" + e.getMessage().substring(5) + " is not a number", this);
        }
    }

    public abstract double apply(double d1, double d2);
}
