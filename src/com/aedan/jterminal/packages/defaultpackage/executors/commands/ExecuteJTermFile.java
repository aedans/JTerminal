package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.bash.BashRuntime;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
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
                "exec [string] [string...]:\n" +
                        "    Executes a file with the name [string].jterm, line by line.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            String dir = args.get(1) + ".jterm";
            String lines = FileUtils.readFile(environment.getDirectory().getFile(dir));
            BashRuntime runtime;
            try {
                runtime = new BashRuntime(lines, input, output);
            } catch (Exception e) {
                throw new CommandHandler.CommandHandlerException(e.getMessage());
            }
            String[] arguments = new String[args.getArgs().size() - 2];
            for (int i = 0; i < args.size() - 2; i++) {
                arguments[i] = args.get(i + 2).value;
            }
            runtime.run(arguments);
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }
}
