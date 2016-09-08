package com.aedan.jterminal.commands.defaultpackage.math.commands;

import com.aedan.jterminal.commands.defaultpackage.math.MathCommand;

/**
 * Created by Aedan Smith on 9/8/2016.
 *
 * Default Command.
 */

public class PowerCommand extends MathCommand {

    public PowerCommand() {
        super("^");
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
