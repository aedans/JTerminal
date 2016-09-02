package com.aedan.jterminal.commands.defaultpackage.io.commands;

import com.aedan.jterminal.Directory;
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

public class MakeDirectory extends Command {

    public MakeDirectory() {
        super("mkdir");
        this.properties[0] = "Creates a directory with the given name.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING);
            output.println(FileUtils.createDirectory(directory.getFile(args.getArg(1).value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
