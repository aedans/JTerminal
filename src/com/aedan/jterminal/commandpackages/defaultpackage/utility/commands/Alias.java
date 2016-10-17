package com.aedan.jterminal.commandpackages.defaultpackage.utility.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

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
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.matches(ArgumentType.STRING, ArgumentType.STRING) != 0)
            throw new CommandHandler.CommandHandlerException("Incorrect arguments given");

        String s = args.get(2).value;
        
        environment.removeCommand(args.get(1).value);

        environment.addCommand(new Command(args.get(1).value, "Aliased Command.", "Executes \"" + args.get(2).value + "\"") {
            @Override
            public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
                    throws CommandHandler.CommandHandlerException {
                environment.getCommandHandler().handleInput(s);
            }
        });

        output.printf("Aliased \"%s\" to \"%s\"\n", args.get(1).value, args.get(2).value);
    }

}
