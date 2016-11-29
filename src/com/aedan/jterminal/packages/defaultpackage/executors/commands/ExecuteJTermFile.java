package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.io.Directory;
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

    public static Object execute(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        try {
            if (args.size() == 1)
                throw new JTerminalException("No arguments given", ExecuteJTermFile.class);

            String dir = args.get(1) + ".jterm";
            String lines = FileUtils.readFile(
                    ((Directory) environment.getEnvironmentVariable("DIR")).subFile(dir)
            );
            JTermRuntime runtime;
            try {
                runtime = new JTermRuntime(lines, input, output);
            } catch (Exception e) {
                throw new JTerminalException(e.getMessage(), ExecuteJTermFile.class);
            }
            Object[] arguments = new Object[args.size() - 2];
            for (int i = 0; i < args.size() - 2; i++) {
                arguments[i] = args.get(i + 2).value;
            }
            return runtime.run(arguments);
        } catch (FileUtils.FileIOException e) {
            throw new JTerminalException(e.getMessage(), ExecuteJTermFile.class);
        }
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        return execute(args, input, output, environment);
    }
}
