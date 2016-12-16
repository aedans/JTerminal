package com.aedan.jterminal.packages.defaultpackage.io.commands;

import com.aedan.jterminal.Environment;
import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.packages.defaultpackage.io.Directory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

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
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        File[] files = ((Directory) environment.getEnvironmentVariable("DIR")).subFile().listFiles();
        ArrayList<File> arrayList = new ArrayList<File>(){
            @Override
            public String toString() {
                String s = "";
                for (File file : this) {
                    s += file.getName() + "\n";
                }
                return s.substring(0, s.length()-1);
            }
        };
        Collections.addAll(arrayList, files);
        return arrayList;
    }
}
