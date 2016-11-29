package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class WhileCommand extends Command {
    WhileCommand() {
        super("whl", "Executes a command until a command returns false.");
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class);

        ArrayList<Object> objects = new ArrayList<>();
        while (!Objects.equals(objects.get(objects.size() - 1), false)) {
            objects.add(environment.getCommandHandler().handleInput(args.get(2).toString(), input, output));
        }
        return objects;
    }
}
