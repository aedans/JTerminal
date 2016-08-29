package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandarguments.CommandArguments;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

class RemoveDirectory extends Command {

    RemoveDirectory() {
        super("rmdir", "Removes the directory with the given name.");
    }

    @Override
    public void parse(CommandInput input, CommandArguments args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        try {
            output.println(FileUtils.removeDirectory(directory.getFile(args.getArg(1).value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
