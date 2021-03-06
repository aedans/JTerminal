package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.io.Directory;
import com.aedan.jterminal.utils.FileUtils;

import java.util.Objects;

/**
 * Created by Aedan Smith on 8/29/2016.
 * <p>
 * Default command.
 */

public class Concatenate extends Command {
    public Concatenate() {
        super("cat");
        this.properties[0] = "Concatenates any number of files.";
        this.properties[1] =
                "cat:\n" +
                        "    Prints all user input line by line. Exited by typing \"exit\".\n" +
                        "    -l: Auto-exits after one line.\n" +
                        "cat [directories]...:\n" +
                        "    Reads each file at [directory] line by line.";
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.matches() == MatchResult.CORRECT_ARGS) {
            String out = "";
            String in = input.nextLine();
            while (!Objects.equals(in.trim().toLowerCase(), "exit")) {
                out += in + "\n";
                if (args.flags.contains("l"))
                    break;
                in = input.nextLine();
            }
            return out.trim();
        } else if (args.matches(String.class) == MatchResult.CORRECT_ARGS) {
            try {
                return FileUtils.readFile(
                        ((Directory) environment.getEnvironmentVariable("DIR")).subFile(args.get(1).toString()), true
                );
            } catch (FileUtils.FileIOException e) {
                throw new JTerminalException(e.getMessage(), this);
            }
        } else {
            String s = "";
            for (int i = 1; i < args.size(); i++) {
                try {
                    s += FileUtils.readFile(
                            ((Directory) environment.getEnvironmentVariable("DIR")).subFile(args.get(i).toString()), true
                    ) + "\n";
                } catch (FileUtils.FileIOException e) {
                    throw new JTerminalException(e.getMessage(), this);
                }
            }
            return s.trim();
        }
    }
}
