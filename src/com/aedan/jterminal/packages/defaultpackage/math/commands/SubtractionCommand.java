package com.aedan.jterminal.packages.defaultpackage.math.commands;

import com.aedan.jterminal.packages.defaultpackage.math.MathCommand;

/**
 * Created by Aedan Smith on 9/5/2016.
 * <p>
 * Default Command.
 */

public class SubtractionCommand extends MathCommand {
    public SubtractionCommand() {
        super("-");
        properties[0] = "Subtracts two numbers.";
    }

    @Override
    public double apply(double d1, double d2) {
        return d1 - d2;
    }
}
