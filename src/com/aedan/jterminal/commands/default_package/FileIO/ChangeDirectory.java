package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

class ChangeDirectory extends Command {

    ChangeDirectory() {
        super("cd", "Changes the active directory.");
    }

    @Override
    public void parse(CommandInput input, String[] args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        File f = directory.getFile(args[1]);
        if (f != null) {
            directory.setDirectory(f);
        }
    }

}
