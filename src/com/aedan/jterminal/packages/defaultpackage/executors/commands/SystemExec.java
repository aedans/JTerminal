package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 10/6/2016.
 * <p>
 * Default command.
 */

public class SystemExec extends Command {
    public SystemExec() {
        super("exect");
        this.properties[0] = "Executes a system command.";
        this.properties[1] =
                "exect [string-command]:\n" +
                        "    Executes command [string-command] in the System terminal.";
    }

    @Override
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class);

        try {
            Process process = Runtime.getRuntime().exec(args.get(1).toString());
            final byte[] buffer = new byte[1024];
            while (process.getInputStream().read(buffer) != -1) {
                for (byte b : buffer) {
                    output.print(String.valueOf((char) b));
                }
            }
            output.println("");
            process.destroy();
            return process.exitValue();
        } catch (Exception e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }
}
