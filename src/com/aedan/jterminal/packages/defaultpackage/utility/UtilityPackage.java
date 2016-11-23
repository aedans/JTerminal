package com.aedan.jterminal.packages.defaultpackage.utility;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.*;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.EmbeddedCommandsParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.EnvironmentVariableParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.GlobalVariableParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.SetGlobalVariableParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection.ConstructorAccessParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection.FieldAccessParser;
import com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection.MethodAccessParser;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default utility Commands.
 */

public class UtilityPackage implements Package {
    @Override
    public void addTo(Environment environment) {
        environment.getCommandHandler().getParser().getParsers().add(new EmbeddedCommandsParser());
        environment.getCommandHandler().getParser().getParsers().add(new EnvironmentVariableParser());
        environment.getCommandHandler().getParser().getParsers().add(new GlobalVariableParser());
        environment.getCommandHandler().getParser().getParsers().add(new SetGlobalVariableParser());
        environment.getCommandHandler().getParser().getParsers().add(new FieldAccessParser());
        environment.getCommandHandler().getParser().getParsers().add(new MethodAccessParser());
        environment.getCommandHandler().getParser().getParsers().add(new ConstructorAccessParser(environment));
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
