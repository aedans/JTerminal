package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.CommandHandler;
import com.aedan.jterminal.environment.Environment;
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
                        "    Recursively removes all files in [directory].";
    }

    @Override
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(this, ArgumentType.STRING);

            output.println(FileUtils.removeDirectory(environment.getDirectory().subFile(args.get(1).value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage(), this);
        }
    }
}
