package com.aedan.jterminal.packages.defaultpackage.utility;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.utility.commandformats.AddGlobalVariable;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.Alias;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.Echo;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.Exit;
import com.aedan.jterminal.packages.defaultpackage.utility.commands.Help;
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
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new EmbeddedCommandsRule(environment));
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new EnvironmentVariableRule(environment));
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new GlobalVariableRule(environment));
        environment.getCommandHandler().getTokenizer().addTokenizerRule(new StringLiteralRule());
        environment.addCommandFormat(new AddGlobalVariable(environment));
        environment.addCommand(new Alias());
        environment.addCommand(new Echo());
        environment.addCommand(new Help(environment));
        environment.addCommand(new Exit());
    }
}
