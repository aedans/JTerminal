package com.aedan.jterminal.commands.defaultpackage.utility;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.utility.commandformats.AddGlobalVariableCommandFormat;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.Echo;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.Help;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.ListProperties;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.Match;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default utility Commands.
 */

public class UtilityPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        destCommandHandler.addCommandFormat(new AddGlobalVariableCommandFormat());
        destCommandHandler.addCommand(new Echo());
        destCommandHandler.addCommand(new Help(destCommandHandler));
        destCommandHandler.addCommand(new ListProperties(destCommandHandler));
        destCommandHandler.addCommand(new Match());
    }

}
