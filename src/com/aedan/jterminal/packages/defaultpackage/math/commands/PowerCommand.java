package com.aedan.jterminal.packages.defaultpackage.math.commands;

import com.aedan.jterminal.packages.defaultpackage.math.MathCommand;

/**
 * Created by Aedan Smith on 9/8/2016.
 * <p>
 * Default Command.
 */

public class PowerCommand extends MathCommand {
    public PowerCommand() {
        super("pow");
        properties[0] = "Outputs a number to the power of a second number.";
    }

    @Override
    public double apply(double d1, double d2) {
        return Math.pow(d1, d2);
    }
}
