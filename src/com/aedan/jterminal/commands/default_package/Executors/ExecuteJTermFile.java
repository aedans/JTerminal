package com.aedan.jterminal.commands.default_package.Executors;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.Output;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/16/2016.
 * <p>
 * Default Command.
 */

class ExecuteJTermFile extends Command {

    private CommandHandler commandHandler;

    ExecuteJTermFile(CommandHandler commandHandler) {
        super("exec -s", "exec", 1, "Executes a .jterm file.");
        this.commandHandler = commandHandler;
    }

    @Override
    public void parse(CommandInput input, String in, Directory directory, Output output) throws CommandHandler.CommandHandlerException {
        try {
            String dir = getArgValues(in)[0] + ".jterm";
            String lines = FileUtils.readFile(directory.getFile(dir));
            for (String s : lines.split("\\n")) {
                commandHandler.handleInput(input, s, output);
            }
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
