package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class IfEquals extends Command {
    IfEquals() {
        super("==");
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, Object.class, Object.class);

        return Objects.equals(args.get(1).get(), args.get(2).get());
    }
}
