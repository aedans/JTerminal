package com.aedan.jterminal.commandpackages.defaultpackage.utility.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by kaden on 10/11/16.
 */
public class Exit extends Command {
    public Exit() {
        super("exit", "Exits the terminal");
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
            System.exit(0);
    }
}