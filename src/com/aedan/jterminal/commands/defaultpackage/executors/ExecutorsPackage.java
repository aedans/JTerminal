package com.aedan.jterminal.commands.defaultpackage.executors;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.executors.commands.ExecuteJTermFile;
import com.aedan.jterminal.commands.defaultpackage.executors.commands.For;
import com.aedan.jterminal.commands.defaultpackage.executors.commands.IfEquals;
import com.aedan.jterminal.commands.defaultpackage.executors.commands.IfNotEquals;

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
        destCommandHandler.addCommand(new IfEquals(destCommandHandler));
        destCommandHandler.addCommand(new IfNotEquals(destCommandHandler));
    }

}
