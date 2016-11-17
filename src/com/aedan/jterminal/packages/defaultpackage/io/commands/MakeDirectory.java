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

public class MakeDirectory extends Command {
    public MakeDirectory() {
        super("mkdir");
        this.properties[0] = "Creates a directory with the given name.";
        this.properties[1] =
                "mkdir [directory]:\n" +
                        "    Creates a directory with the name [directory].";
    }

    @Override
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        try {
            args.checkMatches(this, String.class);

            return FileUtils.createDirectory(environment.getDirectory().subFile(args.get(1).toString()));
        } catch (FileUtils.FileIOException e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }
}
