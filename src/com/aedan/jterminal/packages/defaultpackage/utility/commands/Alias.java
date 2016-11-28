package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.LambdaCommand;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 9/13/2016.
 *
 * Default command.
 */

public class Alias extends Command {
    public Alias() {
        super("alias");
        properties[0] = "Aliases a command to another command.";
        properties[1] =
                "alias [string-name] [string-command]:\n" +
                        "    Creates command [string-name] that executes [string-command].";
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class);

        environment.removeCommand(args.get(1).toString());

        environment.addCommand(new LambdaCommand(
                args.get(1).toString(), args.get(2).toString(), "Aliased Command.", "Executes \"" + args.get(2).toString() + "\"")
        );

        return String.format("Aliased \"%s\" to \"%s\"", args.get(1).value, args.get(2).value);
    }
}
