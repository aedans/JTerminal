package com.aedan.jterminal.commands.default_commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;

import java.util.ArrayList;

/**
 * Created by Aedan Smith on 8/15/2016.
 */

public class Help extends Command {

    private CommandHandler commandHandler;

    public Help(CommandHandler commandHandler) {
        super("help", "help", 0, "Lists all commands and their simple descriptions.");
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        //noinspection unchecked
        int lCommand = commandHandler.getCommands().get(0).getIdentifier().length();
        ArrayList<Command> sCommands = (ArrayList<Command>) commandHandler.getCommands().clone();
        sCommands.sort((o1, o2) -> o1.getIdentifier().compareTo(o2.getIdentifier()));
        for (Command c : sCommands){
            output.println(c.getIdentifier() + getSpaces(lCommand-c.getIdentifier().length() + 4) + c.getQuickDescription());
        }
    }

    private String getSpaces(int num){
        String s = "";
        for (int i = 0; i < num; i++) {
            s += " ";
        }
        return s;
    }

}
