package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.sun.org.apache.xpath.internal.operations.Number;

/**
 * Created by Aedan Smith.
 */

class LessThan extends Command {
    LessThan() {
        super("lt", "Returns true if a number is less than another number.");
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment) throws JTerminalException {
        args.checkMatches(this, Number.class, Number.class);

        return (double) args.get(1).value < (double) args.get(2).value;
    }
}
