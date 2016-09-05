package com.aedan.jterminal.commands.defaultpackage.math;

import com.aedan.jterminal.CommandPackage;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.defaultpackage.math.commands.AdditionCommand;
import com.aedan.jterminal.commands.defaultpackage.math.commands.DivisionCommand;
import com.aedan.jterminal.commands.defaultpackage.math.commands.MultiplicationCommand;
import com.aedan.jterminal.commands.defaultpackage.math.commands.SubtractionCommand;

/**
 * Created by Aedan Smith on 9/5/2016.
 *
 * Default Command Package.
 */

public class MathCommandPackage implements CommandPackage {

    @Override
    public void addCommands(CommandHandler destCommandHandler) {
        destCommandHandler.addCommand(new AdditionCommand());
        destCommandHandler.addCommand(new DivisionCommand());
        destCommandHandler.addCommand(new MultiplicationCommand());
        destCommandHandler.addCommand(new SubtractionCommand());
    }

}
