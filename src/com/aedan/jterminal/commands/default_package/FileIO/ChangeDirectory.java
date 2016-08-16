package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.Output;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * Default Command.
 */

class ChangeDirectory extends Command {

    ChangeDirectory() {
        super("cd -s", "cd", 1, "Changes the active directory.");
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        String dir = getArgValues(in)[0];
        directory.setDirectory(directory.getFile(dir));
    }

}
