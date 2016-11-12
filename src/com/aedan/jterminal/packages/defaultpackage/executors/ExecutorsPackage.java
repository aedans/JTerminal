package com.aedan.jterminal.packages.defaultpackage.executors;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.executors.commands.ExecuteJTermFile;
import com.aedan.jterminal.packages.defaultpackage.executors.commands.For;
import com.aedan.jterminal.packages.defaultpackage.executors.commands.SystemExec;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default Execution Commands.
 */

public class ExecutorsPackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.addCommand(new ExecuteJTermFile());
        environment.addCommand(new For());
        environment.addCommand(new SystemExec());
    }
}
