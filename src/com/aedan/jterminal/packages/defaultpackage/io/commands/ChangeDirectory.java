package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.nio.file.Path;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class ChangeDirectory extends Command {
    public ChangeDirectory() {
        super("cd");
        this.properties[0] = "Changes the active directory.";
        this.properties[1] =
                "cd [directory]:\n" +
                        "    Changes the active directory to [directory].";
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, ArgumentType.STRING);

        Path path = environment.getDirectory().getPath(args.get(1).toString());
        if (path != null) {
            environment.getDirectory().setPath(path);
        }
    }
}
