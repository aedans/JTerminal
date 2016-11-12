package com.aedan.jterminal.packages.defaultpackage.utility;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.*;
import com.aedan.jterminal.packages.defaultpackage.utility.parserules.*;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default utility Commands.
 */

public class UtilityPackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.getCommandHandler().getParser().parseRules.add(new EmbeddedCommandsParser());
        environment.getCommandHandler().getParser().parseRules.add(new EnvironmentVariableParser());
        environment.getCommandHandler().getParser().parseRules.add(new GlobalVariableParser());
        environment.getCommandHandler().getParser().parseRules.add(new StringLiteralParser());
        environment.getCommandHandler().getParser().parseRules.add(new SetGlobalVariableParser());
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
