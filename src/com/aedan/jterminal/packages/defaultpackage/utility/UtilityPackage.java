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
        environment.getCommandHandler().getParser().addTokenizerRule(new EmbeddedCommandsRule());
        environment.getCommandHandler().getParser().addTokenizerRule(new EnvironmentVariableRule());
        environment.getCommandHandler().getParser().addTokenizerRule(new GlobalVariableRule());
        environment.getCommandHandler().getParser().addTokenizerRule(new StringLiteralRule());
        environment.getCommandHandler().getParser().addTokenizerRule(new AddGlobalVariable());
        environment.getCommandHandler().getParser().addTokenizerRule(new MultipleCommandRule());
        environment.addCommand(new Alias());
        environment.addCommand(new Echo());
        environment.addCommand(new Help(environment));
        environment.addCommand(new Exit());
        environment.addCommand(new Set());
    }
}
