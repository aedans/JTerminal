package com.aedan.jterminal.commands.defaultpackage.io;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.io.commandformats.OutputToFileCommandFormat;
import com.aedan.jterminal.commands.defaultpackage.io.commands.*;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A CommandPackage containing all Default io Commands.
 */

public class FileIOPackage implements CommandPackage {

    @Override
    public void addCommands(Environment environment) {
        environment.addCommand(new Concatenate());
        environment.addCommand(new ChangeDirectory());
        environment.addCommand(new ListSubdirectories());
        environment.addCommand(new MakeDirectory());
        environment.addCommand(new Open());
        environment.addCommand(new RemoveDirectory());
        environment.addCommand(new RemoveFile());
        environment.addCommand(new Update());
        environment.addCommandFormat(new OutputToFileCommandFormat());
    }

}
