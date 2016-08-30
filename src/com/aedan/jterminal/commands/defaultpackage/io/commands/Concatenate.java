package com.aedan.jterminal.commands.defaultpackage.io.commands;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.util.Objects;

/**
 * Created by Aedan Smith on 8/29/2016.
 *
 * Default command.
 */

public class Concatenate extends Command {

    public Concatenate() {
        super("cat", "Concatenates any number of files.");
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        if (args.length() == 1){
            String out = "";
            String in = input.nextLine();
            while (!Objects.equals(in.trim().toLowerCase(), "exit")){
                out += in + "\n";
                in = input.nextLine();
            }
            output.print(out);
        } else if (args.length() == 2){
            try {
                output.println(FileUtils.readFile(directory.getFile(args.getArg(1).value)));
            } catch (FileUtils.FileIOException e) {
                throw new CommandHandler.CommandHandlerException(e.getMessage());
            }
        } else {
            String s = "";
            for (int i = 1; i < args.length(); i++) {
                try {
                    s += FileUtils.readFile(directory.getFile(args.getArg(i).value));
                } catch (FileUtils.FileIOException e) {
                    throw new CommandHandler.CommandHandlerException(e.getMessage());
                }
            }
            output.println(s);
        }
    }

}
