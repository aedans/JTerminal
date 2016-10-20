package com.aedan.jterminal.packages.defaultpackage.math.commands;

import com.aedan.jterminal.packages.defaultpackage.math.MathCommand;

/**
 * Created by Aedan Smith on 9/5/2016.
 * <p>
 * Default Command.
 */

public class MultiplicationCommand extends MathCommand {

    public MultiplicationCommand() {
        super("*");
        properties[0] = "Multiplies two numbers.";
    }

    @Override
    public long apply(long l1, long l2) {
        return l1 * l2;
    }

    @Override
    public double apply(double d1, double d2) {
        return d1 * d2;
    }

}
