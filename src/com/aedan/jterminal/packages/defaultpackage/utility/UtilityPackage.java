package com.aedan.jterminal.packages.defaultpackage.utility;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.utility.commandformats.AddGlobalVariable;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.*;
import com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules.EmbeddedCommandsRule;
import com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules.EnvironmentVariableRule;
import com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules.GlobalVariableRule;
import com.aedan.jterminal.packages.defaultpackage.utility.tokenizerrules.StringLiteralRule;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default utility Commands.
 */

public class UtilityPackage implements Package {

    @Override
    public void add(Environment environment) {
        environment.getCommandHandler().getTokenizer().addTokenizerRule(
                new EmbeddedCommandsRule(environment.getCommandHandler())
        );
        environment.getCommandHandler().getTokenizer().addTokenizerRule(
                new EnvironmentVariableRule(environment.getEnvironmentVariables())
        );
        environment.getCommandHandler().getTokenizer().addTokenizerRule(
                new GlobalVariableRule(environment.getGlobalVariables())
        );
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new StringLiteralRule());
        environment.addCommandFormat(new AddGlobalVariable(environment));
        environment.addCommand(new Alias());
        environment.addCommand(new Echo());
        environment.addCommand(new Help(environment));
        environment.addCommand(new Match());
        environment.addCommand(new Exit());
    }
}
