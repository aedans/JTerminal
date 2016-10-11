package com.aedan.jterminal.commandpackages.defaultpackage.io.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.commandhandler.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
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
                        "    Prints all user input line by line. Exited by typing \"exit\"\n" +
                        "cat [directories]...:\n" +
                        "    Reads each file at [directory] line by line.";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        if (args.length() == 1) {
            String out = "";
            String in = input.nextLine();
            while (!Objects.equals(in.trim().toLowerCase(), "exit")) {
                out += in + "\n";
                in = input.nextLine();
            }
            output.print(out);
        } else if (args.length() == 2) {
            try {
                output.println(FileUtils.readFile(environment.getDirectory().getFile(args.get(1).value), true));
            } catch (FileUtils.FileIOException e) {
                throw new CommandHandler.CommandHandlerException(e.getMessage());
            }
        } else {
            String s = "";
            for (int i = 1; i < args.length(); i++) {
                try {
                    s += FileUtils.readFile(environment.getDirectory().getFile(args.get(i).value), true);
                } catch (FileUtils.FileIOException e) {
                    throw new CommandHandler.CommandHandlerException(e.getMessage());
                }
            }
            output.println(s);
        }
    }

}
