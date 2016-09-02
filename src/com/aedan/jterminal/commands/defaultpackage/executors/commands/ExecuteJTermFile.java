package com.aedan.jterminal.commands.defaultpackage.executors.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/16/2016.
 * <p>
 * Default Command.
 */

public class ExecuteJTermFile extends Command {

    private final CommandHandler commandHandler;

    public ExecuteJTermFile(CommandHandler commandHandler) {
        super("exec");
        this.properties[0] = "Executes a .jterm file.";
        this.properties[1] = "exec [string]: Executes a file with the name [string].jterm, line by line.";
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING);
            String dir = args.get(1) + ".jterm";
            String lines = FileUtils.readFile(directory.getFile(dir));
            for (String s : lines.split("\\n")) {
                commandHandler.handleInput(input, s, output);
            }
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
