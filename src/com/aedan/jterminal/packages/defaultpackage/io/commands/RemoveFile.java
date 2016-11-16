package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class RemoveFile extends Command {
    public RemoveFile() {
        super("rm");
        this.properties[0] = "Removes the file with the given name.";
        this.properties[1] =
                "rm [directory]:\n" +
                        "    Removes the file at [directory]";
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        try {
            args.checkMatches(this, String.class);

            output.println(FileUtils.removeFile(environment.getDirectory().subFile(args.get(1).toString())));
        } catch (FileUtils.FileIOException e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }
}
