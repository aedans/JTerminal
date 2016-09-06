package com.aedan.jterminal.commands.defaultpackage;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.executors.ExecutorsPackage;
import com.aedan.jterminal.commands.defaultpackage.io.FileIOPackage;
import com.aedan.jterminal.commands.defaultpackage.math.MathCommandPackage;
import com.aedan.jterminal.commands.defaultpackage.utility.UtilityPackage;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * CommandPackage containing all Default Commands.
 */

public class DefaultPackage implements CommandPackage {

    @Override
    public void addCommands(Environment environment) {
        new FileIOPackage().addCommands(environment);
        new ExecutorsPackage().addCommands(environment);
        new MathCommandPackage().addCommands(environment);
        new UtilityPackage().addCommands(environment);
    }

}
