package com.aedan.jterminal.commandpackages.defaultpackage.executors;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commandpackages.defaultpackage.executors.commands.ExecuteJTermFile;
import com.aedan.jterminal.commandpackages.defaultpackage.executors.commands.For;
import com.aedan.jterminal.commandpackages.defaultpackage.executors.commands.IfEquals;
import com.aedan.jterminal.commandpackages.defaultpackage.executors.commands.IfNotEquals;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A CommandPackage containing all Default Execution Commands.
 */

public class ExecutorsPackage implements CommandPackage {

    @Override
    public void addCommands(Environment environment) {
        environment.addCommand(new ExecuteJTermFile());
        environment.addCommand(new For());
        environment.addCommand(new IfEquals());
        environment.addCommand(new IfNotEquals());
    }

}
