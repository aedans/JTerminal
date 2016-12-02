package com.aedan.jterminal.command;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

public class LambdaCommand extends Command {
    private String command;

    public LambdaCommand(String command) {
        this(command, "", "Lambda Command.", "Executes \"" + command + "\"");
        this.command = command;
    }

    public LambdaCommand(String identifier, String command, String... properties) {
        super(identifier, properties);
        this.command = command;
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        String command = this.command;
        for (int i = 1; i < args.size(); i++) {
            // TODO: Preserve types
            command += " \"" + args.get(i).get() + "\"";
        }

        return environment.getCommandHandler().handleInput(command, input, output);
    }
}
