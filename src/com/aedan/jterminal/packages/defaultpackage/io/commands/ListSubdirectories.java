package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.io.Directory;

import java.io.File;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class ListSubdirectories extends Command {
    public ListSubdirectories() {
        super("ls");
        this.properties[0] = "Lists all subdirectories of the current folder.";
        this.properties[1] =
                "ls:\n" +
                        "    Lists all subdirectories of the current folder.";
    }

    @Override
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        File[] files = ((Directory) environment.getEnvironmentVariable("DIR")).subFile().listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i] = new File(files[i].getName());
        }
        return files;
    }
}
