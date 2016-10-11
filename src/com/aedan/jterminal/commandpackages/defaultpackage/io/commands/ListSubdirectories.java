package com.aedan.jterminal.commandpackages.defaultpackage.io.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class ListSubdirectories extends Command {

    public ListSubdirectories() {
        super("ls");
        this.properties[0] = "Lists all subdirectories of the current folder.";
        this.properties[1] =
                "ls:\n" +
                        "    Lists all subdirectories of the current folder.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.matches() != 0)
            throw new CommandHandler.CommandHandlerException("Incorrect arguments given.");

        //noinspection ConstantConditions
        for (File f : environment.getDirectory().getFile().listFiles()) {
            output.println(f.getName());
        }
    }

}
