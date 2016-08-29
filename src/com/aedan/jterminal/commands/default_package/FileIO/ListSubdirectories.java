package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandArgument;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

class ListSubdirectories extends Command {

    ListSubdirectories() {
        super("ls", "Lists all subdirectories of the current folder.");
    }

    @Override
    public void parse(CommandInput input, CommandArgument[] args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        for (File f : directory.getDirectory().listFiles()) {
            output.println(f.getName());
        }
    }

}
