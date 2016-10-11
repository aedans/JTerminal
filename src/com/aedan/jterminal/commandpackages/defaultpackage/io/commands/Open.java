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

public class Open extends Command {

    public Open() {
        super("open");
        this.properties[0] = "Opens a given file with the default application.";
        this.properties[1] =
                "open [directory]:\n" +
                        "    Opens the file at [directory] with the OS default file opener.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            if (args.matches(ArgumentType.STRING) != 0)
                throw new CommandHandler.CommandHandlerException("Incorrect arguments given.");

            output.println(FileUtils.open(environment.getDirectory().getFile(args.get(1).value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
