package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
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

    public Help() {
        super("help");
        this.properties[0] = "Lists all command and their simple descriptions.";
        this.properties[1] =
                "help:\n" +
                        "    Lists all command and their simple descriptions.\n" +
                        "help [string]:\n" +
                        "    Lists the detailed description of command [string].";
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.size() == 1) {
            //noinspection unchecked
            ArrayList<Command> sCommands = (ArrayList<Command>) environment.getCommands().clone();
            sCommands.sort((o1, o2) -> o1.getIdentifier().compareTo(o2.getIdentifier()));

            ArrayList<String> sIdentifiers = new ArrayList<>();
            ArrayList<String> sDescriptions = new ArrayList<>();
            sCommands.stream().filter(c -> c.getProperty(0) != null).forEach(c -> {
                sIdentifiers.add(c.getIdentifier());
                sDescriptions.add(c.getProperty(0));
            });

            output.printGrid(4, sIdentifiers, sDescriptions);
        } else {
            args.matches(ArgumentType.STRING);
            for (Command c : environment.getCommands()) {
                if (c.getIdentifier().equals(args.get(1).value)) {
                    try {
                        output.println(c.getProperty(1));
                        return;
                    } catch (Exception e) {
                        output.printf("Could not access command \"%s\" description (%s)\n", c.getIdentifier(), e.getMessage());
                        return;
                    }
                }
            }

            throw new JTerminalException("Could not find command \"" + args.get(1).value + "\"", this);
        }
    }
}
