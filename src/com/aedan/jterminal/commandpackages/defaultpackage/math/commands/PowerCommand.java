package com.aedan.jterminal.commandpackages.defaultpackage.math.commands;

import com.aedan.jterminal.commandpackages.defaultpackage.math.MathCommand;

/**
 * Created by Aedan Smith on 9/8/2016.
 * <p>
 * Default Command.
 */

public class PowerCommand extends MathCommand {

    public PowerCommand() {
        super("^");
        properties[0] = "Outputs a number to the power of a second number.";
    }

    @Override
    public long apply(long l1, long l2) {
        return (long) Math.pow(l1, l2);
    }

    @Override
    public double apply(double d1, double d2) {
        return Math.pow(d1, d2);
    }
}
