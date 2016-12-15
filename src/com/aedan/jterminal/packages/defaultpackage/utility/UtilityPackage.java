package com.aedan.jterminal.packages.defaultpackage.utility;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.*;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.EmbeddedCommandsParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.EnvironmentVariableParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection.ReflectionPackage;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.variables.GlobalVariablePackage;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default utility Commands.
 */

public class UtilityPackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.getCommandHandler().getParser().addParser(new EmbeddedCommandsParser(environment));
        environment.getCommandHandler().getParser().addParser(new EnvironmentVariableParser(environment));
        environment.addPackage(new GlobalVariablePackage());
        environment.addPackage(new ReflectionPackage());
        environment.addCommand(new Alias());
        environment.addCommand(new Echo());
        environment.addCommand(new Help());
        environment.addCommand(new Exit());
        environment.addCommand(new Set());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
