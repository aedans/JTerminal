package com.aedan.jterminal.commands.defaultpackage.io.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class ChangeDirectory extends Command {

    public ChangeDirectory() {
        super("cd");
        this.properties[0] = "Changes the active directory.";
        this.properties[1] =
                "cd [directory]:\n" +
                "   Changes the active directory to [directory].";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        args.checkMatches(ArgumentType.STRING);
        File f = directory.getFile(args.get(1).value);
        if (f != null) {
            directory.setDirectory(f);
        }
    }

}
