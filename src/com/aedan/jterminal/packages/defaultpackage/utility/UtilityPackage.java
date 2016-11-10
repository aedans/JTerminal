package com.aedan.jterminal.packages.defaultpackage.utility;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.*;
import com.aedan.jterminal.packages.defaultpackage.utility.operands.AddGlobalVariable;
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
    public void addTo(Environment environment) {
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new EmbeddedCommandsRule());
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new EnvironmentVariableRule());
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new GlobalVariableRule());
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new StringLiteralRule());
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new AddGlobalVariable());
        environment.addCommand(new Alias());
        environment.addCommand(new Echo());
        environment.addCommand(new Help(environment));
        environment.addCommand(new Exit());
        environment.addCommand(new Set());
    }
}
