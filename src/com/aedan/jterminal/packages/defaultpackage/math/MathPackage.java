package com.aedan.jterminal.packages.defaultpackage.math;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.packages.defaultpackage.math.commands.*;

/**
 * Created by Aedan Smith on 9/5/2016.
 * <p>
 * Default Command Package.
 */

public class MathPackage implements Package {

    @Override
    public void addTo(Environment environment) {
        environment.addCommand(new AdditionCommand());
        environment.addCommand(new DivisionCommand());
        environment.addCommand(new MultiplicationCommand());
        environment.addCommand(new PowerCommand());
        environment.addCommand(new SubtractionCommand());
    }
}
