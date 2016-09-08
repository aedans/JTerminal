package com.aedan.jterminal.commands.defaultpackage.math.commands;

import com.aedan.jterminal.commands.defaultpackage.math.MathCommand;
import com.aedan.jterminal.environment.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith on 9/5/2016.
 *
 * Default Command.
 */

public class DivisionCommand extends MathCommand {

    public DivisionCommand() {
        super("/");
        properties[0] = "Divides two numbers.";
    }

    @Override
    public long apply(long l1, long l2) {
        return l1 / l2;
    }

    @Override
    public double apply(double d1, double d2) {
        return d1 / d2;
    }

}
