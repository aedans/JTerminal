package com.aedan.jterminal.commandpackages.defaultpackage.math;

import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.commandpackages.defaultpackage.math.commands.*;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith on 9/5/2016.
 *
 * Default Command Package.
 */

public class MathPackage implements CommandPackage {

    @Override
    public void addCommands(Environment environment) {
        environment.addCommand(new AdditionCommand());
        environment.addCommand(new DivisionCommand());
        environment.addCommand(new MultiplicationCommand());
        environment.addCommand(new PowerCommand());
        environment.addCommand(new SubtractionCommand());
    }

}
