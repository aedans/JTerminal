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

class RemoveFile extends Command {

    RemoveFile() {
        super("rm -s", "rm", 1, "Removes the file with the given name.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            output.println(FileUtils.removeFile(directory.getFile(getArgValues(in)[0])));
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
