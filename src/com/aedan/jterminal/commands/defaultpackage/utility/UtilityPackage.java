package com.aedan.jterminal.commands.defaultpackage.utility;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commands.defaultpackage.utility.commandformats.AddGlobalVariable;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.Echo;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.Help;
import com.aedan.jterminal.commands.defaultpackage.utility.commands.Match;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * A Package containing all Default utility Commands.
 */

public class UtilityPackage implements CommandPackage {

    @Override
    public void addCommands(Environment environment) {
        environment.addCommandFormat(new AddGlobalVariable());
        environment.addCommand(new Echo());
        environment.addCommand(new Help(environment));
        environment.addCommand(new Match());
    }

}
