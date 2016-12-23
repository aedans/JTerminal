package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.StringOutput;

import java.util.*;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class Help extends Command {

    public Help() {
        super("help");
        this.properties[0] = "Lists all commands and their simple descriptions.";
        this.properties[1] =
                "help:\n" +
                        "    Lists all commands and their simple descriptions.\n" +
                        "help [string]:\n" +
                        "    Lists the detailed description of command [string].";
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.size() == 1) {
            //noinspection unchecked
            List<Command> sCommands = new ArrayList<>(environment.getCommands().values());
            Collections.sort(sCommands, (o1, o2) -> o1.getIdentifier().compareTo(o2.getIdentifier()));

            ArrayList<String> sIdentifiers = new ArrayList<>();
            ArrayList<String> sDescriptions = new ArrayList<>();
            sCommands.stream().filter(c -> c.getProperty(0) != null).forEach(c -> {
                sIdentifiers.add(c.getIdentifier());
                sDescriptions.add(c.getProperty(0));
            });

            StringOutput s = new StringOutput();
            s.printGrid(4, sIdentifiers, sDescriptions);
            return s.getString().trim();
        } else {
            args.matches(String.class);
            Command c = environment.getCommands().get(args.get(1).get());
            if (c != null) {
                try {
                    return c.getProperty(1);
                } catch (Exception e) {
                    return String.format("Could not access command \"%s\" description (%s)\n", c.getIdentifier(), e.getMessage());
                }
            }

            throw new JTerminalException("Could not find command \"" + args.get(1).get() + "\"", this);
        }
    }
}
