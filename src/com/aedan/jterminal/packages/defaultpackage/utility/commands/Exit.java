package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by kaden on 10/11/16.
 *
 * Default Command
 */

public class Exit extends Command {
    public Exit() {
        super("exit", "Exits the terminal.", "exit:\n    Exits the terminal.");
    }

    @Override
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        environment.getEnvironmentVariables().put("RUN", false);
        return null;
    }
}
