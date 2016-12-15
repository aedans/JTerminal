package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

public class If extends Command {
    protected If() {
        super("if");
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, Boolean.class, ArgumentList.class);

        if ((boolean) args.get(1).get()){
            return environment.getCommandHandler().handleInput((ArgumentList) args.get(2).get(), input, output);
        } else {
            return null;
        }
    }
}
