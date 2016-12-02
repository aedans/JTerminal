package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 8/14/2016.
 * <p>
 * Default Command.
 */

public class Echo extends Command {
    public Echo() {
        super("echo");
        this.properties[0] = "Outputs text.";
        this.properties[1] =
                "echo [string]:\n" +
                        "    Outputs [string].";
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class);

        return args.get(1).get();
    }
}
