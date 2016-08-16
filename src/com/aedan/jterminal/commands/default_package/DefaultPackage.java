package com.aedan.jterminal.commands.default_package;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.default_package.Executors.ExecutorsPackage;
import com.aedan.jterminal.commands.default_package.FileIO.FileIOPackage;
import com.aedan.jterminal.commands.default_package.Utility.UtilityPackage;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A CommandPackage containing all Default Commands.
 */

public class DefaultPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        new FileIOPackage().addCommands(destCommandHandler);
        new ExecutorsPackage().addCommands(destCommandHandler);
        new UtilityPackage().addCommands(destCommandHandler);
    }

}
