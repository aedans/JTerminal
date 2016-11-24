package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
            ProcessBuilder builder = new ProcessBuilder().command(args.get(1).toString());
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            PrintWriter writer = new PrintWriter(process.getOutputStream());

            Thread inputThread = new Thread(() -> {
                String read = null;
                while (!Thread.interrupted() && (read = input.nextLine()) != null) {
                    writer.println(read);
                    writer.flush();
                }
            });

            inputThread.start();

            String s;
            while ((s = reader.readLine()) != null) {
                output.println(s);
            }

            inputThread.interrupt();

            process.destroy();
            return process.exitValue();
        } catch (Exception e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }
}
