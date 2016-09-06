package com.aedan.jterminal.commands.defaultpackage.utility.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 9/2/2016.
 * <p>
 * Default Command.
 */

public class ListProperties extends Command {

    private CommandHandler commandHandler;

    public ListProperties(CommandHandler commandHandler) {
        super("properties");
        this.properties[0] = "Lists all properties of a given command.";
        this.properties[1] =
                "properties [string]:\n" +
                "   Lists all properties of command [string].";
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING);

        for (Command c : commandHandler.getCommands()){
            if (c.getIdentifier().equals(args.get(1).value)) {
                for (int i = 0; i < c.properties.length; i++) {
                    output.printf("%d: %s\n", i, c.properties[i]);
                }
                return;
            }
        }

        output.printf("Could not find command \"%s\"\n", args.get(1).value);
    }

}
