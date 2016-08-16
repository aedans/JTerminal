package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;

/**
 * Created by Aedan Smith on 8/15/2016.
 *
 * A CommandPackage containing all Default FileIO Commands.
 */

public class FileIOPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        destCommandHandler.addCommand(new ChangeDirectory());
        destCommandHandler.addCommand(new ListSubdirectories());
        destCommandHandler.addCommand(new Open());
        destCommandHandler.addCommandFormat(new OutputToFileCommandFormat());
    }

}
