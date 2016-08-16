package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.output.Output;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Default Command.
 */

public class MakeDirectory extends Command {

    MakeDirectory() {
        super("mkdir -s", "mkdir", 1, "Creates a directory with a given name.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            File file = directory.getFile(getArgValues(in)[0]);
            if (!file.exists()) {
                if (file.mkdir()) {
                    output.println("Created directory at " + file.getAbsolutePath());
                } else {
                    throw new CommandHandler.CommandHandlerException("Could not create directory at " +
                            file.getAbsolutePath() + " (Unknown cause)");
                }
            } else {
                output.println("Directory " + file.getAbsolutePath() + " already exists.");
            }
        } catch (Exception e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
