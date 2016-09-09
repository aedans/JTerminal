package com.aedan.jterminal.commandpackages.defaultpackage.utility.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.ArrayList;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class Help extends Command {

    private final Environment environment;

    public Help(Environment environment) {
        super("help");
        this.properties[0] = "Lists all commands and their simple descriptions.";
        this.properties[1] =
                "help:\n" +
                "    Lists all commands and their simple descriptions.\n" +
                "help [string]:\n" +
                "    Lists the detailed description of command [string].";
        this.environment = environment;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.length() == 1) {
            //noinspection unchecked
            ArrayList<Command> sCommands = (ArrayList<Command>) environment.getCommands().clone();
            sCommands.sort((o1, o2) -> o1.getIdentifier().compareTo(o2.getIdentifier()));

            ArrayList<String> sIdentifiers = new ArrayList<>();
            ArrayList<String> sDescriptions = new ArrayList<>();
            for (Command c : sCommands) {
                sIdentifiers.add(c.getIdentifier());
                try {
                    sDescriptions.add(c.getProperty(0));
                } catch (InvalidPropertyException e) {
                    sDescriptions.add(
                            "Could not access command \"" + c.getIdentifier() + "\" description (" + e.getMessage() + ")");
                }
            }

            output.printGrid(4, sIdentifiers, sDescriptions);
        } else {
            args.checkMatches(ArgumentType.STRING);
            for (Command c : environment.getCommands()){
                if (c.getIdentifier().equals(args.get(1).value)){
                    try {
                        output.println(c.getProperty(1));
                        return;
                    } catch (InvalidPropertyException e) {
                        output.printf("Could not access command \"%s\" description (%s)\n",
                                c.getIdentifier(), e.getMessage());
                        return;
                    }
                }
            }

            output.printf("Could not find command \"%s\"\n", args.get(1).value);
        }
    }

}
