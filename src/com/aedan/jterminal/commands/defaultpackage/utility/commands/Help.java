package com.aedan.jterminal.commands.defaultpackage.utility.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.ArrayList;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class Help extends Command {

    private CommandHandler commandHandler;

    public Help(CommandHandler commandHandler) {
        super("help", "Lists all commands and their simple descriptions.");
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        //noinspection unchecked
        ArrayList<Command> sCommands = (ArrayList<Command>) commandHandler.getCommands().clone();
        sCommands.sort((o1, o2) -> o1.getIdentifier().compareTo(o2.getIdentifier()));

        ArrayList<String> sIdentifiers = new ArrayList<>();
        ArrayList<String> sDescriptions = new ArrayList<>();
        for (Command c : sCommands) {
            sIdentifiers.add(c.getIdentifier());
            sDescriptions.add(c.getQuickDescription());
        }

        output.printGrid(4, sIdentifiers, sDescriptions);
    }

}
