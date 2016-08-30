package com.aedan.jterminal.commands.defaultpackage.executors;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.executors.commands.ExecuteJTermFile;
import com.aedan.jterminal.commands.defaultpackage.executors.commands.For;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A CommandPackage containing all Default Execution Commands.
 */

public class ExecutorsPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        destCommandHandler.addCommand(new ExecuteJTermFile(destCommandHandler));
        destCommandHandler.addCommand(new For(destCommandHandler));
    }

}
