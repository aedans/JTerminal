package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Default Command.
 */

public class MakeDirectory extends Command {

    MakeDirectory() {
        super("mkdir -s", "mkdir", 1, "Creates a directory with the given name.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            output.println(FileUtils.createDirectory(directory.getFile(getArgValues(in)[0])));
        } catch (FileUtils.FileIOException e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
