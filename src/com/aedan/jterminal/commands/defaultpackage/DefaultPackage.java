package com.aedan.jterminal.commands.defaultpackage;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.executors.ExecutorsPackage;
import com.aedan.jterminal.commands.defaultpackage.io.FileIOPackage;
import com.aedan.jterminal.commands.defaultpackage.utility.UtilityPackage;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * CommandPackage containing all Default Commands.
 */

public class DefaultPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        new FileIOPackage().addCommands(destCommandHandler);
        new ExecutorsPackage().addCommands(destCommandHandler);
        new UtilityPackage().addCommands(destCommandHandler);
    }

}
