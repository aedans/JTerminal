package com.aedan.jterminal.commandpackages.defaultpackage.io.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
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
        this.properties[1] =
                "mkdir [directory]:\n" +
                        "    Creates a directory with the name [directory].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            if (args.matches(ArgumentType.STRING) != 0)
                throw new CommandHandler.CommandHandlerException("Incorrect arguments given");

            output.println(FileUtils.createDirectory(environment.getDirectory().getFile(args.get(1).value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
