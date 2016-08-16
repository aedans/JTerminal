package com.aedan.jterminal.commands.default_package.Utility;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default Utility Commands.
 */

public class UtilityPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        destCommandHandler.addCommand(new Echo());
        destCommandHandler.addCommand(new Help(destCommandHandler));
    }

}
