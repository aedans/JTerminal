package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.io.Directory;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class RemoveDirectory extends Command {
    public RemoveDirectory() {
        super("rmdir");
        this.properties[0] = "Removes the directory with the given name.";
        this.properties[1] =
                "rmdir [directory]:\n" +
                        "    Recursively removes all files in [directory].";
    }

    // TODO: Variatic number of files.
    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        try {
            args.checkMatches(this, String.class);

            output.println(FileUtils.removeDirectory(
                    ((Directory) environment.getEnvironmentVariable("DIR")).subFile(args.get(1).toString())
            ));
            return null;
        } catch (FileUtils.FileIOException e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }
}
