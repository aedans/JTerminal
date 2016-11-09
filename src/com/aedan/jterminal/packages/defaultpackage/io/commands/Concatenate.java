package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
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
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws CommandHandler.CommandHandlerException {
        if (args.matches() == MatchResult.CORRECT_ARGS) {
            String out = "";
            String in = input.nextLine();
            while (!Objects.equals(in.trim().toLowerCase(), "exit")) {
                out += in + "\n";
                if (args.isFlagPresent("l"))
                    break;
                in = input.nextLine();
            }
            output.print(out);
        } else if (args.matches(ArgumentType.STRING) == MatchResult.CORRECT_ARGS) {
            try {
                output.println(FileUtils.readFile(environment.getDirectory().getFile(args.get(1).value), true));
            } catch (FileUtils.FileIOException e) {
                throw new CommandHandler.CommandHandlerException(e.getMessage(), this);
            }
        } else {
            String s = "";
            for (int i = 1; i < args.size(); i++) {
                try {
                    s += FileUtils.readFile(environment.getDirectory().getFile(args.get(i).value), true);
                } catch (FileUtils.FileIOException e) {
                    throw new CommandHandler.CommandHandlerException(e.getMessage(), this);
                }
            }
            output.println(s);
        }
    }
}
