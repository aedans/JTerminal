package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.Argument;
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
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class);

        String s = args.get(2).toString();

        environment.removeCommand(args.get(1).toString());

        environment.addCommand(new Command(args.get(1).toString(), "Aliased Command.", "Executes \"" + args.get(2).toString() + "\"") {
            @Override
            public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
                    throws JTerminalException {
                String command = s;
                for (int i = 1; i < args.size(); i++) {
                    Argument argument = args.get(i);
                    command += " \"" + argument.value + "\"";
                }

                return environment.getCommandHandler().handleInput(command, input, output);
            }
        });

        return String.format("Aliased \"%s\" to \"%s\"\n", args.get(1).value, args.get(2).value);
    }
}
