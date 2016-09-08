package com.aedan.jterminal.commands.defaultpackage.executors.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/16/2016.
 * <p>
 * Default Command.
 */

public class ExecuteJTermFile extends Command {

    public ExecuteJTermFile() {
        super("exec");
        this.properties[0] = "Executes a .jterm file.";
        this.properties[1] =
                "exec [string]:\n" +
                "    Executes a file with the name [string].jterm, line by line.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING);
            String dir = args.get(1) + ".jterm";
            String lines = FileUtils.readFile(environment.getDirectory().getFile(dir));
            for (String s : lines.split("\\n")) {
                try {
                    environment.handleInput(input, s, output);
                } catch (CommandHandler.CommandHandlerException e){
                    output.printf("Could not handle command \"%s\" (%s)\n", s, e.getMessage());
                }
            }
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
