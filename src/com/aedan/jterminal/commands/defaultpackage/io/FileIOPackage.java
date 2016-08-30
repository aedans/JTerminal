package com.aedan.jterminal.commands.defaultpackage.io;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.io.commandformats.OutputToFileCommandFormat;
import com.aedan.jterminal.commands.defaultpackage.io.commands.*;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A CommandPackage containing all Default io Commands.
 */

public class FileIOPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        destCommandHandler.addCommand(new Concatenate());
        destCommandHandler.addCommand(new ChangeDirectory());
        destCommandHandler.addCommand(new ListSubdirectories());
        destCommandHandler.addCommand(new MakeDirectory());
        destCommandHandler.addCommand(new Open());
        destCommandHandler.addCommand(new RemoveDirectory());
        destCommandHandler.addCommand(new RemoveFile());
        destCommandHandler.addCommand(new Update());
        destCommandHandler.addCommandFormat(new OutputToFileCommandFormat());
    }

}
