package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Created by Aedan Smith on 10/6/2016.
 * <p>
 * Default command.
 */

public class TerminalExec extends Command {

    public TerminalExec() {
        super("exect");
        this.properties[0] = "Executes a command via the terminal.";
        this.properties[1] = "exect [string-command]:\n" +
                "    Executes command [string-command] in the System terminal (currently only Windows).";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING);

            Process process = Runtime.getRuntime().exec("cmd");
            new Thread(new SyncPipe(process.getErrorStream(), output)).start();
            PrintWriter stdin = new PrintWriter(process.getOutputStream());
            stdin.println(args.get(1));
            stdin.close();
            output.println("\n\nReturn code: " + process.waitFor());
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }
}

class SyncPipe implements Runnable {

    private final CommandOutput out;
    private final InputStream in;

    public SyncPipe(InputStream istrm, CommandOutput ostrm) {
        in = istrm;
        out = ostrm;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (; in.read(buffer) != -1; ) {
                out.print(new String(buffer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
