package com.aedan.jterminal.commands.default_package.Executors;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;

/**
 * Created by Aedan Smith on 8/16/2016.
 *
 * Default Command.
 */

class ExecuteJTermFile extends Command {

    private CommandHandler commandHandler;

    ExecuteJTermFile(CommandHandler commandHandler) {
        super("exec -s", "exec", 1, "Executes a .jterm file.");
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            String dir = getArgValues(in)[0];
            if (dir.endsWith(".jterm")) {
                String lines = FileUtils.readFile(new File(dir));
                for (String s : lines.split("\\n")) {
                    commandHandler.handleString(s, output);
                }
            } else {
                throw new CommandHandler.CommandHandlerException("File \"" + dir + "\" is not a .jterm file");
            }
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
