package com.aedan.jterminal.commands.defaultpackage.io.commands;

import com.aedan.jterminal.utils.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class RemoveDirectory extends Command {

    public RemoveDirectory() {
        super("rmdir");
        this.properties[0] = "Removes the directory with the given name.";
        this.properties[1] =
                "rmdir [directory]:\n" +
                "   Recursively removes all files in [directory].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING);
            output.println(FileUtils.removeDirectory(directory.getFile(args.get(1).value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
