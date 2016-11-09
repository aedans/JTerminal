package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgument;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.ArrayList;

/**
 * Created by Aedan Smith on 9/13/2016.
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
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        String s = args.get(2).value;

        environment.removeCommand(args.get(1).value);

        environment.addCommand(new Command(args.get(1).value, "Aliased Command.", "Executes \"" + args.get(2).value + "\"") {
            @Override
            public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
                    throws CommandHandler.CommandHandlerException {
                String command = s;
                ArrayList<CommandArgument> args1 = args.getArgs();
                for (int i = 1; i < args1.size(); i++) {
                    CommandArgument commandArgument = args1.get(i);
                    command += " \"" + commandArgument.value + "\"";
                }

                environment.getCommandHandler().handleInput(command);
            }
        });

        output.printf("Aliased \"%s\" to \"%s\"\n", args.get(1).value, args.get(2).value);
    }
}
